package com.smart.notification.common.dataAccess

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Project: NotificationApp
 * Package: com.smart.notification.common.dataAccess
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class VolleyAPI constructor(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE: VolleyAPI? = null

        fun getInstance(context: Context) = INSTANCE?: synchronized(this){
            INSTANCE ?: VolleyAPI(context).also {   volley ->
                INSTANCE = volley
            }
        }
    }

    private val requestQueue: RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
    }

    fun<T> addToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }
}