package com.smart.notification.common.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import com.smart.notification.common.entities.AdEntity

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.dataAccess
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Dao
interface AdDao {
    @Query("SELECT * FROM AdEntity")
    fun getAll(): LiveData<MutableList<AdEntity>>

    @Query("SELECT * FROM AdEntity WHERE id = :id")
    fun getById(id: Int): LiveData<AdEntity>

    @Query("SELECT COUNT(*) FROM AdEntity")
    fun getCount(): LiveData<Int>

    @Insert
    suspend fun add(ad: AdEntity): Long

    @Update
    suspend fun upgrade(ad: AdEntity): Int

    @Delete
    suspend fun delete(ad: AdEntity): Int
}