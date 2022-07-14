package com.smart.notification.model

import com.smart.notification.Application
import com.smart.notification.common.dataAccess.SettingsPreference
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Project: NotificationApp
 * Package: com.smart.notification.mainModule.model
 * Update by israel on Wednesday, 7/6/2022 8:52 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class ModelSettings {
    private val settings: SettingsPreference by lazy {
        Application.storage
    }

    fun getDevice(): String? = settings.getValue(Constants.SETTINGS_NAME)

    suspend fun saveDevice(device: String) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Constants.SETTINGS_NAME, device)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }
}