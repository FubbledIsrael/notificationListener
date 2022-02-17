package com.example.samsmx.`class`

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.samsmx.enum.ApplicationPackageName
import com.example.samsmx.enum.InterceptedNotificationCode

class NotificationListener: NotificationListenerService(){
    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    @SuppressLint("HardwareIds")
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        val pack = sbn.packageName
        val extras = sbn.notification.extras
        val title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text").toString()
        var subtext = ""

        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE.value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val b = extras[Notification.EXTRA_MESSAGES] as Array<*>?

                if (b != null) {
                    for (tmp in b) {
                        val msgBundle = tmp as Bundle
                        subtext = msgBundle.getString("text")!!
                    }
                    println("Details1 : $subtext")
                }

                if (subtext.isEmpty()) {
                    subtext = text
                }
                println("Details2 : $subtext")

                val intent = Intent("com.example.samsmx")
                intent.putExtra("Notification Code", notificationCode)
                intent.putExtra("package", pack)
                intent.putExtra("title", title)
                intent.putExtra("text", subtext)
                intent.putExtra("id", sbn.id)
                sendBroadcast(intent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE.value) {
            val activeNotifications = this.activeNotifications
            if (!activeNotifications.isNullOrEmpty()) {
                for (i in activeNotifications.indices) {
                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
                        val intent = Intent("com.example.samsmx.class.NotificationListener")
                        intent.putExtra("Notification Code", notificationCode)
                        sendBroadcast(intent)
                        break
                    }
                }
            }
        }
    }

    private fun matchNotificationCode(sbn: StatusBarNotification): Int {
        val packageName = sbn.packageName
        return if(packageName == ApplicationPackageName.WHATSAPP_PACK_NAME.value)  InterceptedNotificationCode.WHATSAPP_CODE.value else InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE.value
    }
}