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

        if (pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if(sbn.tag.isNullOrEmpty()){
                    val intent = Intent("com.example.samsmx")
                    intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
                    intent.putExtra("cant_msg", sbn.notification.number)
                    intent.putExtra("key", sbn.key)
                    intent.putExtra("priority", sbn.notification.priority)
                    intent.putExtra("user", sbn.user.hashCode())
                    intent.putExtra("code", InterceptedNotificationCode.WHATSAPP_CODE.value)
                    sendBroadcast(intent)
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

    }
}