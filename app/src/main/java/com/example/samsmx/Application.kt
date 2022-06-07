package com.example.samsmx

import android.app.Application
import androidx.room.Room
import com.example.samsmx.common.dataAccess.VolleyAPI
import com.example.samsmx.common.dataAccess.RoomAPI
import com.example.samsmx.common.utils.Constants

class Application: Application() {
    companion object{
        lateinit var database: RoomAPI
        lateinit var volleyAPI: VolleyAPI
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            RoomAPI::class.java,
            Constants.DB_NAME)
            .addMigrations()
            .build()

        volleyAPI = VolleyAPI.getInstance(this)
    }
}