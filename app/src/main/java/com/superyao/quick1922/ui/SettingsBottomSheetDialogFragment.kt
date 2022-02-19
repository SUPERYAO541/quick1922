package com.superyao.quick1922.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.superyao.quick1922.databinding.FragmentSettingsBinding
import com.superyao.quick1922.ui.base.BaseBottomSheetDialogFragment
import com.superyao.quick1922.ui.main.MainViewModel
import com.superyao.quick1922.utils.brightness
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).apply {

        val sharedPreferences = viewModel.getSharedPreferences()

        vibrateWhenScanned.isChecked = sharedPreferences.vibrateWhenScanned
        vibrateWhenScanned.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.vibrateWhenScanned = checked
        }

        exitWhenScanned.isChecked = sharedPreferences.exitWhenScanned
        exitWhenScanned.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.exitWhenScanned = checked
        }

        highestBrightness.isChecked = sharedPreferences.highestBrightness
        highestBrightness.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.highestBrightness = checked
            activity?.brightness(if (checked) 1f else viewModel.screenBrightness)
        }
    }.root

    companion object {
        fun newInstance() = SettingsBottomSheetDialogFragment()
    }
}