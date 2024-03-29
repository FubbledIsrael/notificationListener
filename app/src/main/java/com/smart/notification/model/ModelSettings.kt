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

    //Device
    fun getDevice(): String? = settings.getStringValue(Parameter.DEVICE_PARAM.value)

    suspend fun saveDevice(device: String) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Parameter.DEVICE_PARAM.value, device)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    //Device
    fun getHost(): String? = settings.getStringValue(Parameter.HOST_PARAM.value)

    suspend fun saveHost(host: String) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Parameter.HOST_PARAM.value, host)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    //Application
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

    //Last Time
    fun getLastTime(): Long = settings.getLongValue(Parameter.TIME_PARAM.value)

    suspend fun saveLastTime(time: Long) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Parameter.TIME_PARAM.value, time)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun removeLastTime() = withContext(Dispatchers.IO){
        try {
            settings.removeValue(Parameter.TIME_PARAM.value)
        } catch (e: Exception){
            throw Exception(TypeError.DELETE.name)
        }
    }


    //Last Time
    fun getLastPhone(): String? = settings.getStringValue(Parameter.PHONE_PARAM.value)

    suspend fun saveLastPhone(phone: String) = withContext(Dispatchers.IO){
        try {
            settings.setValue(Parameter.PHONE_PARAM.value, phone)
        } catch (e: Exception){
            throw Exception(TypeError.INSERT.name)
        }
    }

    suspend fun removeLastPhone() = withContext(Dispatchers.IO){
        try {
            settings.removeValue(Parameter.PHONE_PARAM.value)
        } catch (e: Exception){
            throw Exception(TypeError.DELETE.name)
        }
    }
}