package com.superyao.quick1922.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.superyao.dev.toolkit.PermissionsRequest
import com.superyao.dev.toolkit.effectHeavyClickVibrate
import com.superyao.dev.toolkit.ui.clickTooFast
import com.superyao.quick1922.R
import com.superyao.quick1922.databinding.ActivityMainBinding
import com.superyao.quick1922.ui.AboutBottomSheetDialogFragment
import com.superyao.quick1922.ui.SettingsBottomSheetDialogFragment
import com.superyao.quick1922.utils.QRCode1922Scanner
import com.superyao.quick1922.utils.alertDialog
import com.superyao.quick1922.utils.brightness
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), QRCode1922Scanner.Callback {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var scanner: QRCode1922Scanner

    // =============================================================================================
    // Lifecycle
    // =============================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).let {
            binding = it
            setContentView(it.root)
            initUI()
        }
        PermissionsRequest(this, Manifest.permission.CAMERA).apply {
            rationale = getString(R.string.permission_reason)
            dontAskAgainHelpMessage = rationale
            onGranted = { scanner.resume() }
            onDenied = { onBackPressed() }
            onGoToAppDetailsSettings = { onBackPressed() }
        }.request(this)
    }

    override fun onStart() {
        super.onStart()
        scanner.resume()
        highestBrightness(true)
    }

    override fun onStop() {
        super.onStop()
        scanner.pause()
        highestBrightness(false)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    // =============================================================================================
    // Init
    // =============================================================================================

    private fun initUI() {
        viewModel.screenBrightness = window.attributes.screenBrightness

        scanner = QRCode1922Scanner(binding.barcodeView, binding.flashlight, this)

        binding.sms.setOnClickListener {
            if (clickTooFast()) return@setOnClickListener
            startActivity(QRCode1922Scanner.sms1922Intent())
        }

        binding.about.setOnClickListener {
            if (clickTooFast()) return@setOnClickListener
            val fragment = AboutBottomSheetDialogFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.settings.setOnClickListener {
            if (clickTooFast()) return@setOnClickListener
            val fragment = SettingsBottomSheetDialogFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }
    }

    // =============================================================================================
    // Callback
    // =============================================================================================

    override fun onScanSuccess(sms1922Intent: Intent) {
        try {
            startActivity(sms1922Intent)
            if (viewModel.sharedPreferences().vibrateWhenScanned) {
                effectHeavyClickVibrate()
            }
            if (viewModel.sharedPreferences().exitWhenScanned) {
                onBackPressed()
            }
        } catch (e: Exception) {
            Timber.e(e)
            alertDialog(getString(R.string.error_start_sms_app))
                .setOnDismissListener { scanner.resume() }
        }
    }

    override fun onScanNot1922() {
        alertDialog(getString(R.string.error_not_1922_qr))
            .setOnDismissListener { scanner.resume() }
    }

    // =============================================================================================
    // Others
    // =============================================================================================

    private fun highestBrightness(enable: Boolean) {
        if (viewModel.sharedPreferences().highestBrightness) {
            brightness(if (enable) 1f else viewModel.screenBrightness)
        }
    }
}