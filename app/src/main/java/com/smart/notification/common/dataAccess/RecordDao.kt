package com.smart.notification.common.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import com.smart.notification.common.entities.RecordEntity

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.dataAccess
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Dao
interface RecordDao {
    @Query("SELECT COUNT(*) FROM RecordEntity WHERE id_ad = :id_ad")
    fun getCountByAd(id_ad: Int): LiveData<Int>

    @Query("SELECT * FROM RecordEntity WHERE id_ad = :id_ad")
    fun getByAd(id_ad: Int): LiveData<MutableList<RecordEntity>>

    @Query("SELECT * FROM RecordEntity WHERE status = :status")
    fun getByStatus(status: Int): LiveData<MutableList<RecordEntity>>

    @Query("SELECT COUNT(*) FROM RecordEntity WHERE phone = :phone")
    fun getCountByPhone(phone: String): Int

    @Query("DELETE FROM RecordEntity WHERE id_ad = :id_ad")
    suspend fun deleteByAd(id_ad: Int): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(record: RecordEntity): Long

    @Query("UPDATE RecordEntity SET time = :time, id_ad = :id_ad WHERE phone = :phone")
    suspend fun updateTimeAndAd(time: Long, id_ad: Int, phone: String): Int

    @Update
    suspend fun upgrade(record: RecordEntity): Int
}