package com.smart.notification.common.utils

import android.text.Editable
import android.util.Patterns

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Tuesday, 7/19/2022 12:29 PM
 * GitHub: https://github.com/FubbledIsrael
 */

fun getPackageByCode(code: Int): ApplicationPackageName = when(code){
    InterceptedNotificationCode.WHATSAPP_CODE.ordinal -> {
        ApplicationPackageName.WHATSAPP_PACK_NAME
    }
    InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal -> {
        ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME
    }
    else -> {
        ApplicationPackageName.UNKNOWN
    }
}

fun getNotificationByCode(code: Int): InterceptedNotificationCode = when(code){
    InterceptedNotificationCode.WHATSAPP_CODE.ordinal -> {
        InterceptedNotificationCode.WHATSAPP_CODE
    }
    InterceptedNotificationCode.WHATSAPP_DUAL_CODE.ordinal -> {
        InterceptedNotificationCode.WHATSAPP_DUAL_CODE
    }
    InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal -> {
        InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE
    }
    InterceptedNotificationCode.CALL_CODE.ordinal -> {
        InterceptedNotificationCode.CALL_CODE
    }
    InterceptedNotificationCode.MESSAGE_CODE.ordinal -> {
        InterceptedNotificationCode.MESSAGE_CODE
    }
    else -> {
        InterceptedNotificationCode.UNKNOWN
    }
}

fun getNotificationByName(name: String): InterceptedNotificationCode{
    var code = InterceptedNotificationCode.UNKNOWN

    if (name == InterceptedNotificationCode.WHATSAPP_CODE.value)
        code = InterceptedNotificationCode.WHATSAPP_CODE

    if(name == InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value)
        code = InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE

    return code
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun isValidPhoneNumber(phone: String): Boolean {
    return if (phone.trim().length >= 10) Patterns.PHONE.matcher(phone).matches() else false
}