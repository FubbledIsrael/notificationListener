package com.smart.notification.common.entities

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.entities
 * Update by israel on Tuesday, 7/19/2022 12:40 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "RecordEntity", indices = [Index(value = ["phone"], unique = true)])
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo val phone: String = "",
    @ColumnInfo val time: Long = 0L,
    @ColumnInfo val id_ad: Int = 0,
    @ColumnInfo var status: Int = 0){

    @SuppressLint("SimpleDateFormat")
    fun getDateString(): String {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val netDate = Date(this.time)
            format.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}