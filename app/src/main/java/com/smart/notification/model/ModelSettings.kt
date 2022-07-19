package com.smart.notification.model

import com.smart.notification.Application
import com.smart.notification.common.dataAccess.SettingsPreference
import com.smart.notification.common.utils.ApplicationPackageName
import com.smart.notification.common.utils.InterceptedNotificationCode
import com.smart.notification.common.utils.Parameter
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

    fun getDevice(): String? = settings.getStringValue(Parameter.DEVICE_PARAM.value)

    suspend fun saveDevice(device: String) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Parameter.DEVICE_PARAM.value, device)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    fun getApplication(key: String): Int = settings.getIntValue(key)

    fun getNotificationByPackage(id: Int): Int{
        var code = InterceptedNotificationCode.UNKNOWN.ordinal

        if(id == settings.getIntValue(ApplicationPackageName.WHATSAPP_PACK_NAME.value))
            code = InterceptedNotificationCode.WHATSAPP_CODE.ordinal
        else if(id == settings.getIntValue(ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value))
            code = InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal

        return code
    }

    suspend fun saveApplication(key: String, application: Int) = withContext(Dispatchers.IO){
        try {
            settings.setValue(key, application)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun removeApplication(key: String) = withContext(Dispatchers.IO){
        try {
            settings.removeValue(key)
        } catch (e: Exception){
            throw Exception(TypeError.DELETE.name)
        }
    }
}