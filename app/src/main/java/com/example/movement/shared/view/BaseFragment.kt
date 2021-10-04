package com.example.movement.shared.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.movement.R

open class BaseFragment: Fragment() {
    var spinnerDialog: SpinnerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            spinnerDialog = SpinnerDialog(requireContext())
        }
    }

    fun showSpinner() {
        activity?.runOnUiThread {
            if (spinnerDialog?.isShowing != true) {
                spinnerDialog?.show()
            }
        }
    }

    fun dismissSpinner() {
        activity?.runOnUiThread {
            if (spinnerDialog?.isShowing == true) {
                spinnerDialog?.dismiss()
            }
        }
    }

    fun alertShow(
        titleMsg: String? = null,
        bodyMsg: CharSequence?= null,
        okClick: (DialogInterface) -> Unit = {},
    ) {
        context?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            titleMsg?.let {
                builder.setTitle(it)
            }
            bodyMsg?.let {
                builder.setMessage(it)
            }

            builder.setPositiveButton(
                R.string.general_ok
            ) { dialog, _ ->
                okClick(dialog)
            }
        }
    }
}