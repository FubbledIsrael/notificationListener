package com.smart.notification.common.service

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.smart.notification.R
import com.smart.notification.common.utils.toEditable
import com.smart.notification.databinding.DeviceLayoutBinding

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Sunday, 7/10/2022 4:22 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class DeviceDialog(val device: String? = "", private val onSubmitClickListener: (String) -> Unit): DialogFragment() {
    private lateinit var mBinding: DeviceLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = DeviceLayoutBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(mBinding.root)

        if(!device.isNullOrEmpty()) {
            mBinding.etName.text = device.toEditable()
            mBinding.btnAccept.text = getString(R.string.edit)
        }

        mBinding.btnAccept.setOnClickListener {
            val name = mBinding.etName.text.toString()

            mBinding.tilName.error = null

            if(device.equals(name))
                mBinding.tilName.error = getString(R.string.change_device_name)
            else if(name.trim().isEmpty())
                mBinding.tilName.error = getString(R.string.empty_field)
            else {
                onSubmitClickListener.invoke(name)
                dismiss()
            }
        }

        mBinding.btnCancel.setOnClickListener {
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}