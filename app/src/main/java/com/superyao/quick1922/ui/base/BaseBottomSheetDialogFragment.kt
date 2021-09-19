package com.superyao.quick1922.ui.base

import android.content.DialogInterface
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    var onDismiss: (() -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }
}