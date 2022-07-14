package com.smart.notification.model

import androidx.lifecycle.LiveData
import com.smart.notification.Application
import com.smart.notification.common.dataAccess.AdDao
import com.smart.notification.common.dataAccess.RecordDao
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

class ModelRoom {
    private val adDao: AdDao by lazy {
        Application.database.adDao()
    }
    private val recordDao: RecordDao by lazy {
        Application.database.recordDao()
    }

    //Functions Ad
    fun getAdAll(): LiveData<MutableList<AdEntity>> = adDao.getAll()
    fun getAdCount(): LiveData<Int> = adDao.getCount()
    fun getAdById(id: Int): LiveData<AdEntity> = adDao.getById(id)

    suspend fun saveAd(ad: AdEntity) = withContext(Dispatchers.IO){
        try {
            adDao.add(ad)
        } catch (e: Exception){
            println(e.message)
            throw Exception(TypeError.EXIST.name)
        }
    }

    suspend fun removeAd(ad: AdEntity) = withContext(Dispatchers.IO){
        try {
            adDao.delete(ad)
        } catch (e: Exception){
            println(e.message)
            throw Exception(TypeError.DELETE.name)
        }
    }

    //Functions Record
    fun getRecordCountByAd(id_ad: Int): LiveData<Int> = recordDao.getCountByAd(id_ad)
    fun getRecordByAd(id_ad: Int): LiveData<MutableList<RecordEntity>> = recordDao.getByAd(id_ad)

    suspend fun saveRecord(record: RecordEntity) = withContext(Dispatchers.IO){
        try {
            recordDao.add(record)
        } catch (e: Exception){
            println(e.message)
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun updateRecord(record: RecordEntity) = withContext(Dispatchers.IO){
        try{
            recordDao.upgrade(record)
        } catch (e: Exception){
            println(e.message)
            throw Exception(TypeError.UPDATE.name)
        }
    }

    suspend fun removeRecordAd(id_ad: Int) = withContext(Dispatchers.IO) {
        try {
            recordDao.deleteByAd(id_ad)
        } catch (e: Exception){
            println(e.message)
            throw Exception(TypeError.DELETE.name)
        }
    }
}