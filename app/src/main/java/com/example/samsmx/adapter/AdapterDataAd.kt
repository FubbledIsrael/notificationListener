package com.example.samsmx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samsmx.R
import com.example.samsmx.`class`.Ad
import com.example.samsmx.`class`.RegisterPhone

class AdapterDataAd(private val listAd: List<Ad>, private val listRegister: List<RegisterPhone>) : RecyclerView.Adapter<AdapterDataAd.DataAdViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.data_ad, parent, false)
        return DataAdViewHolder(v)
    }

    override fun onBindViewHolder(holder: DataAdViewHolder, position: Int) {
        holder.setData(listAd[position], listRegister[position])
    }

    override fun getItemCount(): Int {
        return listAd.size
    }

    class DataAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvPay: TextView = itemView.findViewById(R.id.tv_pay_ad)
        private var imgPay: ImageView = itemView.findViewById(R.id.ic_pay_ad)
        private var tvId: TextView = itemView.findViewById(R.id.tv_id_ad)
        private var tvCategory: TextView = itemView.findViewById(R.id.tv_category_ad)
        private var tvCity: TextView = itemView.findViewById(R.id.tv_city_ad)
        private var tvPhoneMile: TextView = itemView.findViewById(R.id.tv_phone_ad)

        private var tvLastPhone: TextView = itemView.findViewById(R.id.tv_last_phone)
        private var tvWhatsApp: TextView = itemView.findViewById(R.id.tv_whatsapp)
        private var tvCall: TextView = itemView.findViewById(R.id.tv_call)
        private var tvMessage: TextView = itemView.findViewById(R.id.tv_message)

        fun setData(objectAd: Ad, registerAd: RegisterPhone){
            if(objectAd.status == 1) {
                tvPay.setText(R.string.active)
                imgPay.setImageResource(R.drawable.ic_circle_check)
            }
            else{
                tvPay.setText(R.string.expired)
                imgPay.setImageResource(R.drawable.ic_circle_warning)
            }

            tvId.text = objectAd.id.toString()
            tvCategory.text = objectAd.category
            tvCity.text = objectAd.city
            tvPhoneMile.text = objectAd.phoneMile

            tvLastPhone.text = registerAd.lastPhone
            tvWhatsApp.text = registerAd.countWhatsApp.toString()
            tvCall.text = registerAd.countCall.toString()
            tvMessage.text = registerAd.countMessage.toString()
        }
    }
}