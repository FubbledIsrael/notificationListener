package com.example.samsmx.`class`

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.samsmx.enum.ApplicationPackageName
import com.example.samsmx.enum.InterceptedNotificationCode

class NotificationListener: NotificationListenerService(){
    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    @Suppress("DEPRECATION")
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pack = sbn.packageName

        if (pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value || pack == ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value) {
            val intent = Intent("com.example.samsmx")
            val code = if(pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value) InterceptedNotificationCode.WHATSAPP_CODE.value else InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value
            intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
            intent.putExtra("code", code)
            sendBroadcast(intent)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }
}