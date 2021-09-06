package com.superyao.quick1922.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.superyao.dev.toolkit.ui.clickTooFast
import com.superyao.dev.toolkit.urlIntent
import com.superyao.quick1922.About
import com.superyao.quick1922.BuildConfig
import com.superyao.quick1922.R
import com.superyao.quick1922.databinding.FragmentAboutBinding
import timber.log.Timber

class AboutBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAboutBinding.inflate(inflater, container, false).apply {

        version.text = BuildConfig.VERSION_NAME

        superyao.setOnClickListener {
            if (!openURL(requireContext(), About.FACEBOOK_PAGE)) {
                openURL(requireContext(), About.FACEBOOK_PAGE2)
            }
        }

        googlePlay.setOnClickListener {
            openURL(requireContext(), About.GOOGLE_PLAY_PAGE)
        }

        contact.setOnClickListener {
            if (clickTooFast()) return@setOnClickListener
            val intent = contactIntent()
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }

        /*
        terms
         */

        terms.setOnClickListener {
            openURL(requireContext(), About.TERMS_AND_CONDITIONS)
        }

        privacyPolicy.setOnClickListener {
            openURL(requireContext(), About.PRIVACY_POLICY)
        }

        disclaimer.setOnClickListener {
            openURL(requireContext(), About.DISCLAIMER)
        }

        /*
        libs
         */

        zxingAndroidEmbedded.setOnClickListener {
            openURL(requireContext(), About.ZXING_ANDROID_EMBEDDED)
        }

        /*
        icon
         */

        flaticon.setOnClickListener {
            openURL(requireContext(), About.FREEPIK)
        }

    }.root

    private fun contactIntent(additional: String = ""): Intent {
        val deviceInfo = try {
            "- Info -\n" +
                    "AppId: ${BuildConfig.APPLICATION_ID}\n" +
                    "Ver: ${BuildConfig.VERSION_NAME}\n" +
                    "VerCode: ${BuildConfig.VERSION_CODE}\n" +
                    "SDK: ${Build.VERSION.SDK_INT}\n" +
                    "Manufacturer: ${Build.MANUFACTURER}\n" +
                    "Brand: ${Build.BRAND}\n" +
                    "Model: ${Build.MODEL}\n" +
                    "Device: ${Build.DEVICE}\n" +
                    "Product: ${Build.PRODUCT}\n" +
                    "- Info -"
        } catch (e: Exception) {
            Timber.e(e)
            ""
        }
        Timber.d(deviceInfo)
        val contactIntent = Intent(Intent.ACTION_SEND).apply {
            selector = Intent(Intent.ACTION_SENDTO).apply { data = "mailto:".toUri() }
            putExtra(Intent.EXTRA_EMAIL, arrayOf(About.EMAIL))
            val subject = "[${getString(R.string.app_name)}] ${getString(R.string.feedback)}"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, "$deviceInfo$additional\n\n")
        }
        return Intent.createChooser(contactIntent, getString(R.string.feedback))
    }

    private fun openURL(context: Context, url: String) = try {
        context.startActivity(urlIntent(url))
        true
    } catch (e: Exception) {
        Timber.e(e)
        false
    }

    companion object {
        fun newInstance() = AboutBottomSheetDialogFragment()
    }
}