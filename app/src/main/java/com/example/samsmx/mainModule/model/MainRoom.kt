package com.example.samsmx.mainModule.model

import androidx.lifecycle.LiveData
import com.example.samsmx.Application
import com.example.samsmx.common.dataAccess.DeviceDao
import com.example.samsmx.common.entitie.DeviceEntity
import com.example.samsmx.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Project: samsMx
 * Package: com.example.samsmx.mainModule.model
 * Update by israe on Monday, 5/23/2022 3:13 AM
 * GitHub: https://github.com/FubbledIsrael
 */
class MainRoom {
    private val daoDevice: DeviceDao by lazy {
        Application.database.deviceDao()
    }

    fun getDevice(): LiveData<DeviceEntity> = daoDevice.getDevice()

    suspend fun saveDevice(device: DeviceEntity) = withContext(Dispatchers.IO){
        try {
            daoDevice.addDevice(device)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun updateDevice(device: DeviceEntity) = withContext(Dispatchers.IO){
        try {
            daoDevice.updateDevice(device)
        } catch (e: Exception){
            throw Exception(TypeError.UPDATE.name)
        }
    }
}