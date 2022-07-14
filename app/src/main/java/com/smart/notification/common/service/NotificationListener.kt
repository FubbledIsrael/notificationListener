package com.smart.notification.common.service

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.smart.notification.common.utils.Constants

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.service
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class NotificationListener: NotificationListenerService(){
    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pack = sbn.packageName

        if (pack == Constants.WHATSAPP_PACK_NAME || pack == Constants.WHATSAPP_BUSINESS_PACK_NAME) {
            val intent = Intent("com.example.samsmx")
            intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
            intent.putExtra("key", sbn.key)
            sendBroadcast(intent)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }
}