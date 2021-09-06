package com.superyao.quick1922.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.superyao.dev.toolkit.ui.clickTooFast
import com.superyao.dev.toolkit.urlIntent
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
            if (!openURL(requireContext(), "fb://page/517927788372255")) {
                openURL(requireContext(), "https://www.facebook.com/SUPERYAO541")
            }
        }

        googlePlay.setOnClickListener {
            openURL(
                requireContext(),
                "https://play.google.com/store/apps/dev?id=5829727399068828820"
            )
        }

        contact.setOnClickListener {
            if (clickTooFast()) return@setOnClickListener
            try {
                startActivity(contactIntent())
            } catch (e: Exception) {
                Timber.e(e)
                Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
            }
        }

        /*
        terms
         */

        terms.setOnClickListener {
            openURL(requireContext(), "https://quick1922.flycricket.io/terms.html")
        }

        privacyPolicy.setOnClickListener {
            openURL(requireContext(), "https://quick1922.flycricket.io/privacy.html")
        }

        disclaimer.setOnClickListener {
            openURL(
                requireContext(),
                "https://www.termsfeed.com/live/b933cef4-b2b6-4993-ac66-59288716ea94"
            )
        }

        /*
        libs
         */

        compressor.setOnClickListener {
            openURL(requireContext(), "https://github.com/zetbaitsu/Compressor")
        }

        /*
        icon
         */

        flaticon.setOnClickListener {
            openURL(requireContext(), "https://www.flaticon.com/authors/freepik")
        }

    }.root

    private fun contactIntent(additional: String = ""): Intent {
        val deviceInfo = try {
            "- Info -\n" +
                    "AppId: ${BuildConfig.APPLICATION_ID}\n" +
                    "Ver: ${BuildConfig.VERSION_NAME}\n" +
                    "Code: ${BuildConfig.VERSION_CODE}\n" +
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
            // selector
            // use Intent.ACTION_SENDTO to select the Activity
            selector = Intent(Intent.ACTION_SENDTO).apply { data = "mailto:".toUri() }
            // putExtra
            putExtra(Intent.EXTRA_EMAIL, arrayOf("supersy943@gmail.com"))
            putExtra(
                Intent.EXTRA_SUBJECT,
                "[${getString(R.string.app_name)}] ${getString(R.string.feedback)}"
            )
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