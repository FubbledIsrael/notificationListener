package com.example.samsmx.common.dataAccess

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Project: samsMx
 * Package: com.example.samsmx.common.database
 * Update by israe on Monday, 5/16/2022 2:34 PM
 * GitHub: https://github.com/FubbledIsrael
 */
class VolleyAPI constructor(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE: VolleyAPI? = null

        fun getInstance(context: Context) = INSTANCE?: synchronized(this){
            INSTANCE ?: VolleyAPI(context).also {
                INSTANCE = it
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