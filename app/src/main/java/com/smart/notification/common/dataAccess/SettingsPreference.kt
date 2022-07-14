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
    private val storage = context.getSharedPreferences(Constants.SETTINGS_NAME, 0)

    fun setValue(key: String, value: String){
        storage.edit().putString(key, value).apply()
    }

    fun getValue(key: String): String?{
        return storage.getString(key, null)
    }
}