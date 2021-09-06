package com.superyao.quick1922.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.superyao.dev.toolkit.color
import com.superyao.dev.toolkit.drawable
import com.superyao.dev.toolkit.ui.clickTooFast
import com.superyao.quick1922.R
import timber.log.Timber

class QRCode1922Scanner(
    private val barcodeView: DecoratedBarcodeView,
    private val flashlightButton: ImageView,
    private var callback: Callback,
) {
    interface Callback {
        fun onScanned(sms1922Intent: Intent)
    }

    var torchON = false
        private set

    init {
        val context = barcodeView.context

        barcodeView.run {
            statusView.visibility = View.GONE
            viewFinder.setLaserVisibility(false)
            viewFinder.setMaskColor(context.color(android.R.color.transparent))

            val formats = listOf(BarcodeFormat.QR_CODE)
            decoderFactory = DefaultDecoderFactory(formats)

            decodeContinuous { result ->
                Timber.d(result.text)
                if (result.text?.startsWith(SMS_TO_1922) == true) { // 1922 detected
                    val smsBody = result.text.removePrefix("$SMS_TO_1922:")
                    callback.onScanned(sms1922Intent(smsBody))
                }
            }

            setTorchListener(object : DecoratedBarcodeView.TorchListener {
                override fun onTorchOn() {
                    torchON = true
                    flashlightButton.setImageDrawable(context.drawable(R.drawable.ic_round_flash_on_36))
                }

                override fun onTorchOff() {
                    torchON = false
                    flashlightButton.setImageDrawable(context.drawable(R.drawable.ic_round_flash_off_36))
                }
            })
        }

        flashlightButton.run {
            if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                visibility = View.VISIBLE
                setOnClickListener {
                    if (clickTooFast()) return@setOnClickListener
                    toggleTorch()
                }
            }
        }
    }

    fun resume() {
        barcodeView.resume()
    }

    fun pause() {
        barcodeView.pause()
    }

    private fun toggleTorch() {
        if (torchON) {
            barcodeView.setTorchOff()
        } else {
            barcodeView.setTorchOn()
        }
    }

    companion object {
        const val SMS_TO_1922 = "smsto:1922"

        fun sms1922Intent(smsBody: String = ""): Intent {
            return Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data = SMS_TO_1922.toUri()
                if (smsBody.isNotEmpty()) {
                    putExtra("sms_body", smsBody)
                }
            }
        }
    }
}