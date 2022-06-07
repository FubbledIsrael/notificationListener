package com.example.samsmx.common.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.samsmx.common.entitie.DeviceEntity

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.dataAccess
 * Update by israe on Monday, 5/23/2022 1:29 AM
 * GitHub: https://github.com/FubbledIsrael
 */
@Dao
interface DeviceDao {
    @Query("SELECT * FROM DeviceEntity LIMIT 1")
    fun getDevice(): LiveData<DeviceEntity>

    @Insert
    suspend fun addDevice(device: DeviceEntity): Long

    @Update
    suspend fun updateDevice(device: DeviceEntity): Int
}