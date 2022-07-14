package com.smart.notification.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.entities
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "RecordEntity", indices = [Index(value = ["phone"], unique = true)])
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var phone: String = "",
    var time: Long = 0L,
    var id_ad: Int = 0,
    var status: Int = 0){

    fun getDateString(): String = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault()).format(time)
}
