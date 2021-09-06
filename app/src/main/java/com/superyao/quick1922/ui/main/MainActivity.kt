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
import dagger.hilt.android.AndroidEntryPoint

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
            onDenied = { onBackPressed() }
            onGoToAppDetailsSettings = { onBackPressed() }
        }.request(this)
    }

    override fun onStart() {
        super.onStart()
        scanner.resume()
    }

    override fun onStop() {
        super.onStop()
        scanner.pause()
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

    // =============================================================================================
    // Init
    // =============================================================================================

    private fun initUI() {
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

    override fun onScanned(sms1922Intent: Intent) {
        startActivity(sms1922Intent)
        if (viewModel.sharedPreferences().vibrateOnScanned) {
            effectHeavyClickVibrate()
        }
        if (viewModel.sharedPreferences().autoFinishActivity) {
            onBackPressed()
        }
    }
}