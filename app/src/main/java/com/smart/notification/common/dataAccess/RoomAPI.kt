package com.smart.notification.common.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.entities.RecordEntity

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.dataAccess
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

@Database(entities = [AdEntity::class, RecordEntity::class], version = 1, exportSchema = false)
abstract class RoomAPI: RoomDatabase(){
    abstract fun adDao(): AdDao
    abstract fun recordDao(): RecordDao
}
