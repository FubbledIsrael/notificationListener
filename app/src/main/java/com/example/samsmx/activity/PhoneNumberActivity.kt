package com.example.samsmx.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.example.samsmx.R
import com.example.samsmx.`class`.Ad
import com.example.samsmx.`class`.RegisterPhone
import com.example.samsmx.`class`.ResponseRecordPhone
import com.example.samsmx.`class`.SettingsApplication.Companion.storage
import com.example.samsmx.`class`.VolleySingleton
import com.example.samsmx.adapter.AdapterDataAd
import com.example.samsmx.databinding.ActivityPhoneNumberBinding
import com.example.samsmx.enum.InterceptedNotificationCode
import com.example.samsmx.enum.KeyAd
import com.example.samsmx.enum.KeyHosting
import com.example.samsmx.enum.SettingsNotificationListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.util.*

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding
    private var imageChangeBroadcastReceiver: ReceiveBroadcastReceiver? = null
    private lateinit var objectAd: List<Ad>
    private lateinit var registerAd: List<RegisterPhone>
    private var phoneList: MutableList<String> = mutableListOf()
    private lateinit var nameDevice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        try {
            loadPreferences()
            binding.tvNameDevice.text = nameDevice
            binding.recycleView.layoutManager = LinearLayoutManager(this)

            refreshRecycleView()
        }catch (e: Exception){
            storage.wipe()
            Snackbar.make(binding.linearlayout, R.string.error , Snackbar.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        if (!isNotificationServiceEnabled()) {
            val enableNotificationListenerAlertDialog: AlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog.show()
        }

        binding.btnRefresh.setOnClickListener {
            for(item in 0..registerAd.size){
                registerAd[item].lastPhone = getString(R.string.waiting)
                registerAd[item].countWhatsApp = 0
                registerAd[item].countCall = 0
                registerAd[item].countMessage = 0
            }
            phoneList.clear()
        }

        imageChangeBroadcastReceiver = ReceiveBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.samsmx")
        registerReceiver(imageChangeBroadcastReceiver, intentFilter)
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(this)
        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton(R.string.accept) { dialog, _ ->
            dialog.dismiss()
            startActivity(Intent(SettingsNotificationListener.ACTION_NOTIFICATION_LISTENER_SETTINGS.value))
        }
        alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        return alertDialogBuilder.create()
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = this.contentResolver
        val enabledNotificationListeners = Settings.Secure.getString(contentResolver, SettingsNotificationListener.ENABLED_NOTIFICATION_LISTENERS.value)
        val packageName = this.packageName

        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName))
    }

    class ReceiveBroadcastReceiver(private val phoneNumberActivity: PhoneNumberActivity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val code = intent.getIntExtra("code", -1)
            val phone = intent.getStringExtra("phone").toString()
            var index = -1

            if (isValidPhoneNumber(phone)) {
                when(code){
                    InterceptedNotificationCode.WHATSAPP_CODE.value -> {
                        index = 0
                    }
                    InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value -> {
                        index = 1
                    }
                    InterceptedNotificationCode.CALL_CODE.value -> {

                    }
                    InterceptedNotificationCode.MESSAGE_CODE.value -> {

                    }
                }

                if(index != -1){
                    if(phoneNumberActivity.phoneList.indexOf(phone) == -1){

                        val category = phoneNumberActivity.objectAd[index].category
                        val city = phoneNumberActivity.objectAd[index].id_city.toString()
                        phoneNumberActivity.registerAd[index].lastPhone = phone

                        phoneNumberActivity.registerAd[index].countWhatsApp = phoneNumberActivity.registerAd[index].countWhatsApp + 1

                        registerPhone(phoneNumberActivity, phone, category, city)
                    }
                }
            }
        }

        private fun isValidPhoneNumber(phone: String): Boolean {
            return if (phone.trim().length > 10) Patterns.PHONE.matcher(phone).matches() else false
        }

        private fun registerPhone(context: Context, phone: String, category: String, idCity: String){
            val stringRequest = object : StringRequest(
                Method.POST, KeyHosting.UrlRecordPhone.url,
                { response ->
                    try {
                        val jsonResponse = Gson().fromJson(response , ResponseRecordPhone::class.java)
                        if(jsonResponse.error == 0){
                            phoneNumberActivity.refreshRecycleView()
                            phoneNumberActivity.savePreferences()

                            phoneNumberActivity.phoneList.add(phone)
                        }
                    }catch (e: Exception){
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    }
                },
                {
                    Toast.makeText(context, context.getString(R.string.warning) + ": " + it.message, Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["function"] = "add"
                    params["user"] = phoneNumberActivity.nameDevice
                    params["phone"] = phone
                    params["category"] = category
                    params["city"] = idCity
                    return params
                }
            }

            stringRequest.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                1f
            )

            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
        }
    }

    private fun loadPreferences(){
        nameDevice = storage.getValueString(KeyAd.DeviceName.value).toString()
        objectAd = Gson().fromJson(storage.getValueString(KeyAd.DataJson.value), Array<Ad>::class.java).toList()
        registerAd = Gson().fromJson(storage.getValueString(KeyAd.RegisterJson.value), Array<RegisterPhone>::class.java).toList()
    }

    private fun savePreferences(){
        val jsonDataAd = Gson().toJson(objectAd)
        val jsonRegisterAd = Gson().toJson(registerAd)
        storage.setValue(KeyAd.DataJson.value, jsonDataAd)
        storage.setValue(KeyAd.RegisterJson.value, jsonRegisterAd)
    }

    fun refreshRecycleView(){
        val adapterAd = AdapterDataAd(objectAd, registerAd)
        binding.recycleView.adapter = adapterAd
    }

    override fun onBackPressed() {
        super.onBackPressed()
        storage.wipe()
        phoneList.clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        storage.wipe()
        phoneList.clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        return super.onSupportNavigateUp()
    }
}