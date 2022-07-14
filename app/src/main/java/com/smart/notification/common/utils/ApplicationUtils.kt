package com.smart.notification.common.utils

import android.content.Context
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

fun getMsgErrorByCode(errorCode: String?): Int = when(errorCode){
    TypeError.INSERT.name -> R.string.error
    TypeError.EXIST.name -> R.string.error_exists
    TypeError.DELETE.name -> R.string.error
    TypeError.GET.name -> R.string.error
    TypeError.EMPTY.name -> R.string.empty_field
    TypeError.LENGTH.name -> R.string.verify_field_length
    TypeError.UPDATE.name -> R.string.error
    else -> R.string.error
}

fun getNameAppByCode(code: Int): Int = when(code){
    InterceptedNotificationCode.WHATSAPP_CODE.ordinal -> {
        R.string.whatsapp
    }
    InterceptedNotificationCode.WHATSAPP_DUAL_CODE.ordinal -> {
        R.string.dual_whatsapp
    }
    InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal -> {
        R.string.business_whatsapp
    }
    InterceptedNotificationCode.CALL_CODE.ordinal -> {
        R.string.call
    }
    InterceptedNotificationCode.MESSAGE_CODE.ordinal -> {
        R.string.message
    }
    else -> {
        R.string.error
    }
}

fun getCodeAppByName(name: String): Int{
    var code = InterceptedNotificationCode.UNKNOWN.ordinal

    if (name == Constants.WHATSAPP)
        code = InterceptedNotificationCode.WHATSAPP_CODE.ordinal

    if(name == Constants.WHATSAPP_BUSINESS)
        code = InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal

    return code
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun hideKeyboard(context: Context, view: View){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

private fun isValidPhoneNumber(phone: String): Boolean {
    return if (phone.trim().length >= 10) Patterns.PHONE.matcher(phone).matches() else false
}