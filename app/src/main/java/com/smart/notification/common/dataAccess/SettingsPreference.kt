package com.smart.notification.common.dataAccess

import android.content.Context
import com.smart.notification.common.utils.Constants

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.entities
 * Update by israel on Wednesday, 7/6/2022 2:38 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class SettingsPreference(context: Context) {
    private val storage = context.getSharedPreferences(Constants.SETTINGS_NAME, Context.MODE_PRIVATE)

    fun setValue(key: String, value: String){
        storage.edit().putString(key, value).apply()
    }

    fun setValue(key: String, value: Int){
        storage.edit().putInt(key, value).apply()
    }

    fun setValue(key: String, value: Long){
        storage.edit().putLong(key, value).apply()
    }

    fun getStringValue(key: String): String?{
        return storage.getString(key, null)
    }

    fun getIntValue(key: String): Int{
        return storage.getInt(key, 0)
    }

    fun getLongValue(key: String): Long{
        return storage.getLong(key, 0L)
    }

    fun removeValue(key: String){
        storage.edit().remove(key).apply()
    }
}