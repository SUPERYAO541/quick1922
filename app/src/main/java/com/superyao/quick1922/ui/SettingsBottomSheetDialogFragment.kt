package com.superyao.quick1922.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.superyao.quick1922.databinding.FragmentSettingsBinding
import com.superyao.quick1922.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).apply {

        val sharedPreferences = viewModel.sharedPreferences()

        vibrateOnScanned.isChecked = sharedPreferences.vibrateOnScanned
        vibrateOnScanned.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.vibrateOnScanned = checked
        }

        autoFinishActivity.isChecked = sharedPreferences.autoFinishActivity
        autoFinishActivity.setOnCheckedChangeListener { _, checked ->
            sharedPreferences.autoFinishActivity = checked
        }
    }.root

    companion object {
        fun newInstance() = SettingsBottomSheetDialogFragment()
    }
}