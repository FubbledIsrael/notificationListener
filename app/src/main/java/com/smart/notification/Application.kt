package com.smart.notification

import android.app.Application
import androidx.room.Room
import com.smart.notification.common.dataAccess.VolleyAPI
import com.smart.notification.common.dataAccess.RoomAPI
import com.smart.notification.common.dataAccess.SettingsPreference
import com.smart.notification.common.utils.Constants

/**
 * Project: NotificationApp
 * Package: com.smart.notification
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class Application: Application() {
    companion object{
        lateinit var database: RoomAPI
        lateinit var volleyAPI: VolleyAPI
        lateinit var storage: SettingsPreference
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            RoomAPI::class.java,
            Constants.DB_NAME)
            .addMigrations()
            .build()

        volleyAPI = VolleyAPI.getInstance(this)

        storage = SettingsPreference(this)
    }
}