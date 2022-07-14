package com.smart.notification.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.notification.R
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.utils.getNotificationAppByInt
import com.smart.notification.databinding.AdCardBinding

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.adapters
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class AdListAdapter(private var listener: OnClickListenerAd) : ListAdapter<AdEntity, RecyclerView.ViewHolder>(DiffUtilCallbackAd()) {
    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mBinding = AdCardBinding.bind(view)

        fun setListener(ad: AdEntity){
            with(mBinding){
                btnInformation.setOnClickListener {
                    listener.onInformation(ad)
                }

                icDelete.setOnClickListener{
                    listener.onDelete(ad)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.ad_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ad = getItem(position)

        with(holder as ViewHolder) {
            setListener(ad)
            val drawable = if (ad.status == 1) R.drawable.ic_circle_check else R.drawable.ic_circle_warning
            val idString = "#" + ad.id.toString()

            mBinding.tvApplication.text = mContext.getString(getNotificationAppByInt(ad.app).id)
            mBinding.icExpired.setImageResource(drawable)
            mBinding.tvId.text = idString
            mBinding.tvCity.text = ad.city
            mBinding.tvClassification.text = ad.classification
            mBinding.tvPhone.text = ad.formatPhone()
            mBinding.tvDevice.text = ad.device
        }
    }
}

class DiffUtilCallbackAd: DiffUtil.ItemCallback<AdEntity>(){
    override fun areItemsTheSame(oldAd: AdEntity, newAd: AdEntity): Boolean {
        return oldAd.id == newAd.id
    }

    override fun areContentsTheSame(oldAd: AdEntity, newAd: AdEntity): Boolean {
        return oldAd == newAd
    }
}

