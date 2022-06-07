package com.example.samsmx.enum

enum class InterceptedNotificationCode(val value: Int) {
    WHATSAPP_CODE_PRIORITY_HIGH(1),
    WHATSAPP_CODE_PRIORITY_LOW(0),
    WHATSAPP_CODE(0),
    WHATSAPP_BUSINESS_CODE(3),
    CALL_CODE(1),
    MESSAGE_CODE(2)
}