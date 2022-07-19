package com.smart.notification.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.notification.R
import com.smart.notification.common.entities.RecordEntity
import com.smart.notification.databinding.PhoneCardBinding

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.adapters
 * Update by israel on Thursday, 7/14/2022 10:59 AM
 * GitHub: https://github.com/FubbledIsrael
 */

class RecordListAdapter: ListAdapter<RecordEntity, RecyclerView.ViewHolder>(DiffUtilCallbackRecord()) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mBinding = PhoneCardBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.phone_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val record = getItem(position)

        with(holder as ViewHolder) {
            val drawable = if (record.status == 1) R.drawable.ic_circle_check else R.drawable.ic_circle_warning

            mBinding.tvPhone.text = record.phone
            mBinding.tvDate.text = record.getDateString()
            mBinding.icNotification.setImageResource(drawable)
        }
    }
}

class DiffUtilCallbackRecord: DiffUtil.ItemCallback<RecordEntity>(){
    override fun areItemsTheSame(oldAd: RecordEntity, newAd: RecordEntity): Boolean {
        return oldAd.id == newAd.id
    }

    override fun areContentsTheSame(oldAd: RecordEntity, newAd: RecordEntity): Boolean {
        return oldAd == newAd
    }
}

