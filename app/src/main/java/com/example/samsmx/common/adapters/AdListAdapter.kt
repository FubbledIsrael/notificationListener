package com.example.samsmx.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.samsmx.R
import com.example.samsmx.common.entitie.AdEntity
import com.example.samsmx.common.utils.getNameAppByCode
import com.example.samsmx.databinding.AdLayoutBinding

/**
 * Project: samsMx
 * Package: com.example.samsmx.mainModule.adapter
 * Update by israe on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class AdListAdapter() : ListAdapter<AdEntity, RecyclerView.ViewHolder>(DiffUtilCallback()) {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.ad_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ad = getItem(position)

        with(holder as ViewHolder) {
            mBinding.tvApplication.text = mContext.getString(getNameAppByCode(ad.app))

            val drawable = if (ad.onActive) R.drawable.ic_circle_check
                            else R.drawable.ic_circle_warning

            mBinding.icExpired.setImageResource(drawable)

            mBinding.tvCategory.text = ad.category
            mBinding.tvCity.text = ad.city
            mBinding.tvPhone.text = ad.phone
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mBinding = AdLayoutBinding.bind(view)
    }
}

private class DiffUtilCallback: DiffUtil.ItemCallback<AdEntity>(){
    override fun areItemsTheSame(oldItem: AdEntity, newItem: AdEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdEntity, newItem: AdEntity): Boolean {
        return oldItem == newItem
    }
}

