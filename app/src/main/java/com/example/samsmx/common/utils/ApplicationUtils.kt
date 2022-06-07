package com.example.samsmx.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.samsmx.R


/**
 * Project: samsMx
 * Package: com.example.samsmx.common.utils
 * Update by israe on Saturday, 5/21/2022 10:13 AM
 * GitHub: https://github.com/FubbledIsrael
 */
fun getMsgErrorByCode(errorCode: String?): Int = when(errorCode){
    TypeError.INSERT.name -> R.string.error
    TypeError.EXIST.name -> R.string.error_exist
    TypeError.DELETE.name -> R.string.error
    TypeError.GET.name -> R.string.error
    TypeError.EMPTY.name -> R.string.empty_field
    TypeError.LENGTH.name -> R.string.verify_length
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

fun hideKeyboard(context: Context, view: View){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}