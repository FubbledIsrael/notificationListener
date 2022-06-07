package com.example.samsmx.common.service

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.samsmx.common.utils.Constants

class NotificationListener: NotificationListenerService(){
    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    @Suppress("DEPRECATION")
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pack = sbn.packageName

        if (pack == Constants.WHATSAPP_PACK_NAME || pack == Constants.WHATSAPP_BUSINESS_PACK_NAME) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && sbn.tag != null) {
                val intent = Intent("com.example.samsmx")
                intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
                intent.putExtra("key", sbn.key)
                intent.putExtra("notification", sbn.notification.channelId)
                //intent.putExtra("code", if(pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value) InterceptedNotificationCode.WHATSAPP_CODE.value else InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value)
                sendBroadcast(intent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }
}