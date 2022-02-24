package com.example.samsmx.`class`

import android.content.Context

class Preferences(context: Context)  {
    private val sharedName = "SettingsPreferences"
    private val storage = context.getSharedPreferences(sharedName, 0)

    fun setValue(key: String, value: String){
        storage.edit().putString(key, value).apply()
    }

    fun setValue(key: String, value: Int){
        storage.edit().putInt(key, value).apply()
    }

    fun getValueString(key: String): String?{
        return storage.getString(key, null)
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}