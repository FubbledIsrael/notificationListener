package com.example.samsmx.common.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.samsmx.common.entitie.RecordEntity

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.database
 * Update by israe on Monday, 5/16/2022 2:29 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Dao
interface RecordDao {
    @Query("SELECT * FROM RecordEntity")
    fun getAllRecord(): LiveData<MutableList<RecordEntity>>

    @Query("DELETE FROM RecordEntity")
    fun deleteAllRecord()

    @Insert
    suspend fun addRecord(record: RecordEntity): Long

    @Update
    suspend fun updateRecord(record: RecordEntity): Int
}