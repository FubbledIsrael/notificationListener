package com.example.samsmx.common.entitie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "AdEntity", indices = [Index(value = ["phone", "app"], unique = true)])
data class AdEntity(
    @PrimaryKey var id: Long = 0L,
    var phone: String = "",
    var app: Int = 0,
    var pc: String = "",
    var city: String = "",
    var id_city: Int = 0,
    var category: String = "",
    var lastUpdate: String = "",
    var dateExpired: String = "",
    var channel: String = "",
    var onActive: Boolean = false)