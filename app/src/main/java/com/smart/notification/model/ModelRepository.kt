package com.smart.notification.model

import androidx.lifecycle.LiveData
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.entities.RecordEntity
import com.smart.notification.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Project: NotificationApp
 * Package: com.smart.notification.mainModule.model
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class ModelRepository {
    private val settings = ModelSettings()
    private val room = ModelRoom()

    //Functions Device
    fun getDevice(): String? = settings.getDevice()
    fun getApplication(key: String): Int = settings.getApplication(key)
    fun getNotificationByPackage(id: Int): Int = settings.getNotificationByPackage(id)

    suspend fun saveDevice(device: String) = withContext(Dispatchers.IO){
        if(device.isNotEmpty())
            settings.saveDevice(device)
        else
            throw Exception(TypeError.EMPTY.name)
    }

    suspend fun saveApplication(key: String, application: Int) = withContext(Dispatchers.IO){
        try {
            settings.saveApplication(key, application)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun removeApplication(key: String) = withContext(Dispatchers.IO){
        try {
            settings.removeApplication(key)
        } catch (e: Exception){
            throw Exception(TypeError.DELETE.name)
        }
    }

    //Functions Ad
    fun getAdAll(): LiveData<MutableList<AdEntity>> = room.getAdAll()
    fun getAdCount(): LiveData<Int> = room.getAdCount()
    fun getAdById(id: Int): LiveData<AdEntity> = room.getAdById(id)

    suspend fun saveAd(ad: AdEntity) = withContext(Dispatchers.IO){
        if (ad.id != 0)
            room.saveAd(ad)
        else
            throw Exception(TypeError.INSERT.name)
    }

    suspend fun updateAd(ad: AdEntity) = withContext(Dispatchers.IO){
        room.updateAd(ad)
    }

    suspend fun removeAd(ad: AdEntity) = withContext(Dispatchers.IO){
        room.removeAd(ad)
    }

    //Functions Record
    fun getRecordCountByAd(id_ad: Int): LiveData<Int> = room.getRecordCountByAd(id_ad)
    fun getRecordByAd(id_ad: Int): LiveData<MutableList<RecordEntity>> = room.getRecordByAd(id_ad)
    fun getRecordByStatus(status: Int): LiveData<MutableList<RecordEntity>> = room.getRecordByStatus(status)

    suspend fun saveRecord(record: RecordEntity)= withContext(Dispatchers.IO){
        room.saveRecord(record)
    }

    suspend fun removeRecordAd(id_ad: Int) = withContext(Dispatchers.IO) {
        if (id_ad != 0)
            room.removeRecordAd(id_ad)
        else
            throw Exception(TypeError.DELETE.name)
    }
}