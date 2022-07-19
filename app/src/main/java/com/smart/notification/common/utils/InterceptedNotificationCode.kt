package com.smart.notification.common.utils

import com.smart.notification.R

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

enum class InterceptedNotificationCode(val value: String,val id: Int){
    UNKNOWN("UNKNOWN", R.string.error),
    WHATSAPP_CODE("WhatsApp", R.string.whatsapp),
    WHATSAPP_DUAL_CODE("WhatsApp Dual", R.string.dual_whatsapp),
    WHATSAPP_BUSINESS_CODE("WhatsApp Business", R.string.business_whatsapp),
    CALL_CODE("Call", R.string.call),
    MESSAGE_CODE("Message", R.string.message)
}