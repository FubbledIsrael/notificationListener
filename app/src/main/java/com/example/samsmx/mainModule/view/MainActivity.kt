package com.example.samsmx.mainModule.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.samsmx.R
import com.example.samsmx.common.entitie.DeviceEntity
import com.example.samsmx.common.utils.Constants
import com.example.samsmx.databinding.ActivityMainBinding
import com.example.samsmx.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel
    //private var imageChangeBroadcastReceiver: ReceiveBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupActionBar()
        setupViewModel()
        setupObservers()
        setupButtons()
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)

        /*try {
            //loadPreferences()
            //binding.tvNameDevice.text = nameDevice
            binding.recycleView.layoutManager = LinearLayoutManager(this)

            /////refreshRecycleView()
        }catch (e: Exception){
            storage.wipe()
            //Snackbar.make(binding.viewContainer, R.string.error , Snackbar.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        if (!isNotificationServiceEnabled()) {
            val enableNotificationListenerAlertDialog: AlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog.show()
        }

       /* binding.btnRefresh.setOnClickListener {
            registerPhone.clear()

            registerAd[0] = RegisterPhone(getString(R.string.waiting), 0,0,0)

            if(registerAd.size > 1)
                registerAd[1] = RegisterPhone(getString(R.string.waiting), 0, 0, 0)

            //wipeRegisterPreferences()
            //refreshRecycleView()
        }*/

        imageChangeBroadcastReceiver = ReceiveBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.samsmx")
        registerReceiver(imageChangeBroadcastReceiver, intentFilter)*/
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupObservers() {

    }

    private fun setupButtons() {
        mBinding.btnAdd.setOnClickListener {

        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //supportActionBar?.title = if(mIsEditMode)  getString(R.string.edit_store_title_edit) else getString(R.string.edit_store_title_add)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit -> {
                val mDialogView = layoutInflater.inflate(R.layout.device_layout, null)

                MaterialAlertDialogBuilder(this)
                    .setView(mDialogView)
                    .setTitle(R.string.edit)
                    .setPositiveButton(R.string.accept) { dialog, _ ->
                        val inputLayout: TextInputLayout = mDialogView.findViewById(R.id.tilNameDevice)
                        val editText: TextInputEditText = mDialogView.findViewById(R.id.etNameDevice)
                        val name = editText.text.toString()
                        inputLayout.error = null

                        if(name.isNotEmpty()){
                            val device = DeviceEntity(id = 1, name = name)
                            mMainViewModel.addDevice(device)
                            dialog.dismiss()
                        }
                        else
                            inputLayout.error = getString(R.string.required_field)

                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(this)
        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton(R.string.accept) { dialog, _ ->
            dialog.dismiss()
            startActivity(Intent(Constants.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        return alertDialogBuilder.create()
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = this.contentResolver
        val enabledNotificationListeners = Settings.Secure.getString(contentResolver, Constants.ENABLED_NOTIFICATION_LISTENERS)
        val packageName = this.packageName

        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName))
    }*/

    /*class ReceiveBroadcastReceiver(private val phoneNumberActivity: MainActivity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val code = intent.getIntExtra("code", -1)
            val phone = intent.getStringExtra("phone").toString()
            val key = intent.getStringExtra("key")
            val notification = intent.getStringExtra("notification")
            var index = -1

            if (isValidPhoneNumber(phone)) {
                if (!filterPhoneNumber(phoneNumberActivity.registerPhone, phone.trim())) {
                    phoneNumberActivity.registerPhone.add(phone.trim())

                    /*when (code) {
                        InterceptedNotificationCode.WHATSAPP_CODE.value -> {

                            if(notification.equals(ApplicationPackageName.WHATSAPP_CODE_PRIORITY_HIGH.value))
                                index = 0

                            if(notification.equals(ApplicationPackageName.WHATSAPP_CODE_PRIORITY_LOW.value))
                                index = 1
                        }
                        InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value -> {
                            index = 1
                        }
                        InterceptedNotificationCode.CALL_CODE.value -> {

                        }
                        InterceptedNotificationCode.MESSAGE_CODE.value -> {

                        }
                    }*/

                    println("code : $code\n phone: $phone\n notification: $notification\n key: $key\n index: $index")

                    /*if (index != -1) {
                        val category = phoneNumberActivity.objectAd[index].category
                        val city = phoneNumberActivity.objectAd[index].id_city.toString()
                        println("category: $category \n city: $city")
                        phoneNumberActivity.registerAd[index].lastPhone = phone

                        //registerPhone(phoneNumberActivity, phone, category, city, code, index)
                    }*/
                }

            }
        }

        private fun isValidPhoneNumber(phone: String): Boolean {
            return if (phone.trim().length > 10) Patterns.PHONE.matcher(phone).matches() else false
        }

        private fun filterPhoneNumber(
            phoneList: MutableList<String>,
            phoneNumber: String
        ): Boolean {
            if (phoneList.isNotEmpty())
                phoneList.forEach {
                    if (phoneNumber == it)
                        return true
                }

            return false
        }*/

        /* private fun registerPhone(context: Context, phone: String, category: String, idCity: String, code: Int, index: Int){
            val stringRequest = object : StringRequest(
                Method.POST, KeyHosting.UrlRecordPhone.url,
                { response ->
                    try {
                        println("response: $response")
                        //val jsonResponse = Gson().fromJson(response , ResponseRecordPhone::class.java)

                        /*if (code == InterceptedNotificationCode.WHATSAPP_CODE.value || code == InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.value){
                            when(jsonResponse.error){
                                0 -> {
                                    phoneNumberActivity.registerAd[index].countRegistered = phoneNumberActivity.registerAd[index].countRegistered + 1
                                }
                                1-> {
                                    phoneNumberActivity.registerAd[index].countIgnored = phoneNumberActivity.registerAd[index].countIgnored + 1
                                }
                                2 -> {
                                    phoneNumberActivity.registerAd[index].countBlock = phoneNumberActivity.registerAd[index].countBlock + 1
                                }
                            }

                            phoneNumberActivity.refreshRecycleView()
                            phoneNumberActivity.savePreferences()
                        }*/
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
        }*/
    //}

    /*private fun loadPreferences(){
        nameDevice = storage.getValueString(KeyDevice.DeviceName.value).toString()
        objectAd = Gson().fromJson(storage.getValueString(KeyDevice.DataJson.value), Array<Ad>::class.java).toList()
        registerAd = Gson().fromJson(storage.getValueString(KeyDevice.RegisterJson.value), Array<RegisterPhone>::class.java).toList() as MutableList<RegisterPhone>
    }

    private fun wipeRegisterPreferences(){
        val jsonRegisterAd = Gson().toJson(registerAd)
        storage.setValue(KeyDevice.RegisterJson.value, jsonRegisterAd)
    }

    private fun savePreferences(){
        val jsonDataAd = Gson().toJson(objectAd)
        val jsonRegisterAd = Gson().toJson(registerAd)
        storage.setValue(KeyDevice.DataJson.value, jsonDataAd)
        storage.setValue(KeyDevice.RegisterJson.value, jsonRegisterAd)
    }

    fun refreshRecycleView(){
        val adapterAd = AdapterRegisterAd(objectAd, registerAd)
        binding.recycleView.adapter = adapterAd
    }

    override fun onBackPressed() {
        super.onBackPressed()
        storage.wipe()
    }

    override fun onSupportNavigateUp(): Boolean {
        storage.wipe()
        return super.onSupportNavigateUp()
    }*/
}