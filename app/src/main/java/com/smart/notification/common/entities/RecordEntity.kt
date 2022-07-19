package com.smart.notification.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.entities
 * Update by israe on Tuesday, 7/19/2022 12:40 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "RecordEntity", indices = [Index(value = ["phone"], unique = true)])
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var phone: String = "",
    var time: Long = 0L,
    var id_ad: Int = 0,
    var status: Int = 0){

    fun getDateString(): String {
        val stamp = Timestamp(this.time)
        return Date(stamp.time).toString()
    }
}