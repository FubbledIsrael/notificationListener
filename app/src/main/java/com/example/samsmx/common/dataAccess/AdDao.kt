package com.example.samsmx.common.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.samsmx.common.entitie.AdEntity

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.database
 * Update by israe on Monday, 5/16/2022 12:31 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Dao
interface AdDao {
    @Query("SELECT * FROM AdEntity")
    fun getAllAd(): LiveData<MutableList<AdEntity>>

    @Query("SELECT COUNT(*) FROM AdEntity")
    fun getCountAd(): LiveData<Int>

    @Query("SELECT * FROM AdEntity WHERE id = :id")
    fun getAdById(id: Long): LiveData<AdEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAd(ad: AdEntity): Long

    @Update
    suspend fun updateAd(ad: AdEntity): Int

    @Delete
    suspend fun deleteAd(ad: AdEntity): Int
}