package com.example.samsmx.mainModule.model

import androidx.lifecycle.LiveData
import com.example.samsmx.common.entitie.DeviceEntity
import com.example.samsmx.common.utils.TypeError

/**
 * Project: samsMx
 * Package: com.example.samsmx.mainModule.model
 * Update by israe on Thursday, 5/19/2022 6:41 PM
 * GitHub: https://github.com/FubbledIsrael
 */
class MainRepository {
    private val room = MainRoom()

    fun getDevice(): LiveData<DeviceEntity> = room.getDevice()

    suspend fun saveDevice(device: DeviceEntity){
        if(device.name.isNotEmpty())
            room.saveDevice(device)
        else
            throw Exception(TypeError.EMPTY.name)
    }

    suspend fun updateDevice(device: DeviceEntity){
        if(device.name.isNotEmpty())
            room.updateDevice(device)
        else
            throw Exception(TypeError.EMPTY.name)
    }
}