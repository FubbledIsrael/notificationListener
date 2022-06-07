package com.example.samsmx.common.entitie

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.entitie
 * Update by israe on Sunday, 5/22/2022 9:36 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Entity(tableName = "DeviceEntity")
data class DeviceEntity(
    @PrimaryKey var id: Long = 0L,
    var name: String = "")
