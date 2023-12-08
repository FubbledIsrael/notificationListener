package com.smart.notification.common.utils


/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.utils
 * Update by israel on Saturday, 7/16/2022 10:17 AM
 * GitHub: https://github.com/FubbledIsrael
 */
enum class Parameter(val value: String) {
    BASE_URL("https://api.redcorporativa.com"),
    DEFAULT_LOAD_URL("https://api.redcorporativa.com/app"),
    MEMBERS_PATH("/members"),
    CONTROLLER_PATH("/controller"),
    AD_PATH_FILE("/anuncios.php"),
    RECORD_PATH_FILE("/recordphone.php"),
    ID_PARAM("id"),
    GET_ID_PARAM("getId"),
    POST_ID_PARAM("postId"),
    FUNCTION_PARAM("function"),
    CONTROLLER_PARAM("controller"),
    RECORD_PHONE_PARAM("recordPhone"),
    DEVICE_PARAM("device"),
    HOST_PARAM("host"),
    ERROR_PARAM("error"),
    DATA_PARAM("data"),
    PHONE_PARAM("phone"),
    PACKAGE_PARAM("package"),
    TIME_PARAM("time"),
    AD_PARAM("ad"),
    POST_SAVE_PARAM("postSave"),
    POST_PARAM("post"),
    WHATSAPP_PARAM("whatsapp"),
    WHATSAPP_DUAL_PARAM("whatsapp_dual"),
    WHATSAPP_BUSINESS_PARAM("whatsapp_business")
}