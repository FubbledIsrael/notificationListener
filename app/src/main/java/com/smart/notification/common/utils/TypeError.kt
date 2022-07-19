package com.smart.notification.common.utils

import com.smart.notification.R

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

enum class TypeError(val id: Int) {
    UNKNOWN(R.string.error),
    GET(R.string.error),
    EXIST(R.string.error_exists),
    LENGTH(R.string.verify_field_length),
    EMPTY(R.string.empty_field),
    INSERT(R.string.error),
    UPDATE(R.string.error),
    DELETE(R.string.error)
}