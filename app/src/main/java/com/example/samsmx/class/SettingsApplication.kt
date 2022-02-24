package com.example.samsmx.`class`

import android.annotation.SuppressLint
import android.app.Application

class SettingsApplication: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var storage: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        storage = Preferences(applicationContext)
    }
}