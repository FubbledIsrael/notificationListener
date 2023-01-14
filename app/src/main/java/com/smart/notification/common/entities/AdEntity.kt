package com.smart.notification.common.entities

import android.telephony.PhoneNumberUtils
import androidx.room.ColumnInfo
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
    @ColumnInfo var phone: String = "",
    @ColumnInfo var app: Int = 0,
    @ColumnInfo var device: String = "",
    @ColumnInfo var city: String = "",
    @ColumnInfo var classification: String = "",
    @ColumnInfo var status: Int = 0){

    fun formatPhone(): String = PhoneNumberUtils.formatNumber(this.phone, Locale.getDefault().country)
}