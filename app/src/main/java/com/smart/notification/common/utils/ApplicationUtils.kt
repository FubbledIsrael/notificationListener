package com.smart.notification.common.utils

import android.content.Context
import android.media.AsyncPlayer
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.smart.notification.R

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

fun getNotificationAppByInt(id: Int): InterceptedNotificationCode{
    var code = InterceptedNotificationCode.UNKNOWN

    if (id == InterceptedNotificationCode.WHATSAPP_CODE.ordinal)
        code = InterceptedNotificationCode.WHATSAPP_CODE

    if(id == InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal)
        code = InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE

    return code
}

fun getNotificationAppByName(name: String): InterceptedNotificationCode{
    var code = InterceptedNotificationCode.UNKNOWN

    if (name == InterceptedNotificationCode.WHATSAPP_CODE.value)
        code = InterceptedNotificationCode.WHATSAPP_CODE

    if(name == InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value)
        code = InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE

    return code
}

fun getPackageByInt(id: Int): ApplicationPackageName{
    var code = ApplicationPackageName.UNKNOWN

    if (id == InterceptedNotificationCode.WHATSAPP_CODE.ordinal)
        code = ApplicationPackageName.WHATSAPP_PACK_NAME

    if(id == InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal)
        code = ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME

    return code
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun hideKeyboard(context: Context, view: View){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun isValidPhoneNumber(phone: String): Boolean = if (phone.trim().length >= 10) Patterns.PHONE.matcher(phone).matches() else false