package com.smart.notification.common.adapters

import com.smart.notification.common.entities.AdEntity

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.adapters
 * Update by israel on Saturday, 7/9/2022 12:02 AM
 * GitHub: https://github.com/FubbledIsrael
 */

interface OnClickListenerAd {
    fun onInformation(ad: AdEntity)
    fun onDelete(ad: AdEntity)
}