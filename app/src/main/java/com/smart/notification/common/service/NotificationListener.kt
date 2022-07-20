package com.smart.notification.common.service

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.smart.notification.common.utils.ApplicationPackageName
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.Parameter

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
            intent.putExtra(Parameter.PHONE_PARAM.value, sbn.notification.extras.getString(Notification.EXTRA_TITLE))
            intent.putExtra(Parameter.PACKAGE_PARAM.value, pack)
            intent.putExtra(Parameter.TIME_PARAM.value, System.currentTimeMillis())
            intent.putExtra(Parameter.POST_PARAM.value, Constants.OFF_STATUS)
            sendBroadcast(intent)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)

        val pack = sbn?.packageName

        if (pack == ApplicationPackageName.WHATSAPP_PACK_NAME.value || pack == ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value) {
            val intent = Intent(ApplicationPackageName.APP_PACK_NAME.value)
            intent.putExtra(Parameter.PACKAGE_PARAM.value, pack)
            intent.putExtra(Parameter.POST_PARAM.value, Constants.ON_STATUS)
            sendBroadcast(intent)
        }
    }
}