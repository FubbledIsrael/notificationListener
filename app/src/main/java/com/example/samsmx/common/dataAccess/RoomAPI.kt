package com.example.samsmx.common.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samsmx.common.entitie.AdEntity
import com.example.samsmx.common.entitie.DeviceEntity
import com.example.samsmx.common.entitie.RecordEntity

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.database
 * Update by israe on Monday, 5/16/2022 12:25 PM
 * GitHub: https://github.com/FubbledIsrael
 */
@Database(entities = [AdEntity::class, RecordEntity::class, DeviceEntity::class], version = 1)
abstract class RoomAPI: RoomDatabase(){
    abstract fun adDao(): AdDao
    abstract fun recordDao(): RecordDao
    abstract fun deviceDao(): DeviceDao
}
