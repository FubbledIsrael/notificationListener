package com.smart.notification.common.service

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.smart.notification.R
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.utils.getNotificationByName
import com.smart.notification.databinding.AdLayoutBinding

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.service
 * Update by israel on Sunday, 7/10/2022 6:39 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class AdDialog(private val onSubmitClickListener: (AdEntity) -> Unit): DialogFragment() {
    private lateinit var mBinding: AdLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = AdLayoutBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(mBinding.root)

        val apps = resources.getStringArray(R.array.applications)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_app, apps)

        mBinding.etApplication.setAdapter(adapter)

        mBinding.btnAccept.setOnClickListener {
            val etId = mBinding.etId.text.toString()
            val etApplication = mBinding.etApplication.text.toString()

            mBinding.tilId.error = null
            mBinding.tilApplication.error = null

            if(etId.trim().isEmpty())
                mBinding.tilId.error = getString(R.string.empty_field)

            if(etApplication.trim().isEmpty())
                mBinding.tilApplication.error = getString(R.string.empty_field)

            if(etId.trim().isNotEmpty() && etApplication.trim().isNotEmpty()) {
                val ad = AdEntity(id = etId.toInt(), app = getNotificationByName(etApplication).ordinal)
                onSubmitClickListener.invoke(ad)
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