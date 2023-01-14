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
import com.smart.notification.databinding.HostUrlLayoutBinding

class HostDialog(val url: String? = "", private val onSubmitClickListener: (String) -> Unit): DialogFragment() {
    private lateinit var mBinding: HostUrlLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = HostUrlLayoutBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(mBinding.root)

        if(!url.isNullOrEmpty()){
            mBinding.etName.text = url.toEditable()
            mBinding.btnAccept.text = getString(R.string.edit)
        }

        mBinding.btnAccept.setOnClickListener{
            val name = mBinding.etName.text.toString()

            mBinding.tilName.error = null

            if(url.equals(name))
                mBinding.tilName.error = getString(R.string.error)
            else if(name.trim().isEmpty())
                mBinding.tilName.error = getString(R.string.empty_field)
            else{
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