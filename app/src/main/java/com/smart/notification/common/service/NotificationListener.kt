package com.smart.notification.common.service

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.smart.notification.common.utils.ApplicationPackageName
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

        if (pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value || pack == ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value) {
            val intent = Intent(ApplicationPackageName.APP_PACK_NAME.value)
            intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
            intent.putExtra("package", pack)
            intent.putExtra("time", System.currentTimeMillis())
            intent.putExtra("post", Constants.ON_STATUS)
            sendBroadcast(intent)
        }
    }

    /*override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)

                int notificationCode = matchNotificationCode(sbn);

        if(notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {

            StatusBarNotification[] activeNotifications = this.getActiveNotifications();

            if(activeNotifications != null && activeNotifications.length > 0) {
                for (int i = 0; i < activeNotifications.length; i++) {
                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
                        Intent intent = new  Intent("com.example.ssa_ezra.whatsappmonitoring");
                        intent.putExtra("Notification Code", notificationCode);
                        sendBroadcast(intent);
                        break;
                    }
                }
            }
        }

        val pack = sbn?.packageName

        if (pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value || pack == ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value) {
            val intent = Intent(ApplicationPackageName.APP_PACK_NAME.value)
            intent.putExtra("phone", sbn.notification.extras.getString(Notification.EXTRA_TITLE))
            intent.putExtra("package", pack)
            intent.putExtra("time", System.currentTimeMillis())
            intent.putExtra("post", Constants.OFF_STATUS)
            sendBroadcast(intent)
        }
    }*/
}