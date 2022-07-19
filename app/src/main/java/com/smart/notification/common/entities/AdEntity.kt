package com.smart.notification.common.entities

import android.telephony.PhoneNumberUtils
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.entities
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "AdEntity", indices = [Index(value = ["app"], unique = true)])
data class AdEntity(
    @PrimaryKey var id: Int = 0,
    var phone: String = "",
    var app: Int = 0,
    var device: String = "",
    var city: String = "",
    var id_city: Int = 0,
    var classification: String = "",
    var lastUpdate: String = "",
    var expired: String = "",
    var status: Int = 0){

    fun formatPhone(): String = PhoneNumberUtils.formatNumber(this.phone, Locale.getDefault().country)
}