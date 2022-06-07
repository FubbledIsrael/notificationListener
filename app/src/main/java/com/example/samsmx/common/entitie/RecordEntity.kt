package com.example.samsmx.common.entitie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.entitie
 * Update by israe on Monday, 5/16/2022 11:54 AM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "RecordEntity", indices = [Index(value = ["phone"], unique = true)])
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var phone: String = "",
    var time: String = "",
    var id_ad: Int = 0,
    var onRegister: Boolean = false)
