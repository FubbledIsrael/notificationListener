package com.smart.notification.mainModule.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.smart.notification.R
import com.smart.notification.databinding.ActivityMainBinding
import com.smart.notification.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.smart.notification.Application
import com.smart.notification.mainModule.adapter.AdListAdapter
import com.smart.notification.mainModule.adapter.OnClickListenerAd
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.entities.RecordEntity
import com.smart.notification.common.service.AdDialog
import com.smart.notification.common.service.DeviceDialog
import com.smart.notification.common.service.HostDialog
import com.smart.notification.common.utils.*
import com.smart.notification.dataAdModule.view.DataAdFragment
import com.smart.notification.dataAdModule.viewModel.DataAdViewModel
import org.json.JSONObject
import kotlin.collections.HashMap

/**
 * Project: NotificationApp
 * Package: com.smart.notification.mainModule.view
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class MainActivity : AppCompatActivity(), OnClickListenerAd {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mDataAdViewModel: DataAdViewModel
    private lateinit var mAdapter: AdListAdapter
    private lateinit var imageChangeBroadcastReceiver: ReceiveBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setupObservers()
        setupButtons()
    }

    override fun onStart() {
        super.onStart()

        if (!isNotificationServiceEnabled())
            setupNotificationSnack()

        if(mMainViewModel.getDevice().isNullOrEmpty())
            setupDeviceSnack().show()
        else
            supportActionBar?.title = mMainViewModel.getDevice()

        if(mMainViewModel.getHost().isNullOrEmpty())
            mMainViewModel.updateHost(Parameter.DEFAULT_LOAD_URL.value)
    }

    //Setup
    private fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(Constants.HIDE)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mDataAdViewModel = ViewModelProvider(this)[DataAdViewModel::class.java]

        mAdapter = AdListAdapter(this)
        mBinding.recycleView.apply {
            setHasFixedSize(Constants.HIDE)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        imageChangeBroadcastReceiver = ReceiveBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ApplicationPackageName.APP_PACK_NAME.value)
        registerReceiver(imageChangeBroadcastReceiver, intentFilter)
    }

    private fun setupObservers() {
        mMainViewModel.getAdAll().observe(this){    adList ->
            mBinding.tvMessage.visibility = if(adList.size > 0) View.GONE else View.VISIBLE

            adList.sortBy {    ad ->
                ad.app
            }

            mAdapter.submitList(adList)
        }

        mMainViewModel.getAdCount().observe(this){  total ->
            mBinding.btnAdd.visibility = if(total == 2) View.GONE else View.VISIBLE
        }
    }

    private fun setupButtons() {
        mBinding.btnAdd.setOnClickListener {
            AdDialog(
                onSubmitClickListener = {   ad ->
                    mMainViewModel.setProgressBar(Constants.SHOW)
                    getAd(ad.id.toString(), ad.app)
                }
            ).show(supportFragmentManager, Parameter.AD_PARAM.value)
        }

        mBinding.swipeRefresh.setOnRefreshListener {

            if(mMainViewModel.getApplication(ApplicationPackageName.WHATSAPP_PACK_NAME.value) != 0)
                getUpdateAd(mMainViewModel.getApplication(ApplicationPackageName.WHATSAPP_PACK_NAME.value), InterceptedNotificationCode.WHATSAPP_CODE.ordinal)

            if(mMainViewModel.getApplication(ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value) != 0)
                getUpdateAd(mMainViewModel.getApplication(ApplicationPackageName.WHATSAPP_BUSINESS_PACK_NAME.value), InterceptedNotificationCode.WHATSAPP_BUSINESS_CODE.ordinal)

            mBinding.swipeRefresh.isRefreshing = false
        }
    }

    //Setup Menu Options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ad, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit_device -> {
                DeviceDialog(
                    device = mMainViewModel.getDevice(),
                    onSubmitClickListener = {   name ->
                        mMainViewModel.updateDevice(name)
                        supportActionBar?.title = name
                    }
                ).show(supportFragmentManager, Parameter.DEVICE_PARAM.value)
                return true
            }
            R.id.action_edit_url -> {
                HostDialog(
                    url = mMainViewModel.getHost(),
                    onSubmitClickListener = {   name ->
                        mMainViewModel.updateHost(name)
                    }
                ).show(supportFragmentManager, Parameter.HOST_PARAM.value)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Verify Notification Service
    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = this.contentResolver
        val enabledNotificationListeners = Settings.Secure.getString(contentResolver, SettingsNotificationListener.ENABLED_NOTIFICATION_LISTENERS.value)
        val packageName = this.packageName

        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName))
    }

    //Alert Dialog
    private fun setupNotificationSnack(){
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.notification_listener_service)
            .setMessage(R.string.notification_listener_service_explanation)
            .setPositiveButton(R.string.accept){ dialog, _ ->
                startActivity(Intent(SettingsNotificationListener.ACTION_NOTIFICATION_LISTENER_SETTINGS.value))
                dialog.dismiss()
            }
            .setCancelable(Constants.HIDE)
            .show()
    }

    private fun setupDeviceSnack(): Snackbar {
        return Snackbar.make(mBinding.root, R.string.change_device_name, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.setup){
                DeviceDialog(
                    onSubmitClickListener = {   name ->
                        mMainViewModel.saveDevice(name)
                        supportActionBar?.title = name
                    }
                ).show(supportFragmentManager, Parameter.DEVICE_PARAM.value
                )
            }
    }

    private fun launchAdDataFragment(ad: AdEntity){
        mDataAdViewModel.setShowButtonAdd(
            if(mBinding.btnAdd.visibility == View.VISIBLE)
                Constants.SHOW
            else Constants.HIDE)
        mDataAdViewModel.setAdSelect(ad)

        val fragment = DataAdFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //ReceiveBroadcastReceiver
    class ReceiveBroadcastReceiver(private val activity: MainActivity) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            val phone = intent.getStringExtra(Parameter.PHONE_PARAM.value).toString()
            val pack = intent.getStringExtra(Parameter.PACKAGE_PARAM.value).toString()
            val time = intent.getLongExtra(Parameter.TIME_PARAM.value, 0L)
            val id = activity.mMainViewModel.getApplication(pack)

            if (isValidPhoneNumber(phone)) {
                val record = RecordEntity(
                    phone = phone,
                    time = time,
                    id_ad = id,
                    status = Constants.OFF_STATUS
                )

                activity.mMainViewModel.saveRecord(record)
                activity.registerPhone(record)
            }
        }
    }

    private fun getUpdateAd(id: Int, appCode: Int){
        val url = if(mMainViewModel.getHost().isNullOrEmpty()) Parameter.DEFAULT_LOAD_URL.value else mMainViewModel.getHost()

        val stringRequest = object : JsonObjectRequest(
            Method.GET, "$url/$id", null,
            { response ->
                try {
                    val dataObject = response.getJSONObject(Parameter.DATA_PARAM.value)
                    val ad = AdEntity(dataObject.getInt("id"), dataObject.getString("phone"), 0, dataObject.getString("device"), dataObject.getJSONObject("city").getString("name"), dataObject.getJSONObject("classification").getString("name"), dataObject.getJSONObject("status").getInt("id"))
                    ad.app = appCode
                    mMainViewModel.updateAd(ad)
                }catch (e: Exception){
                    println(e.toString())
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            {   volleyError ->
                println(volleyError.toString())
            }){}

        stringRequest .retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )

        Application.volleyAPI.addToRequestQueue(stringRequest)
    }

    //Load Ad on Server
    private fun getAd(id: String, appCode: Int){
        val url = if(mMainViewModel.getHost().isNullOrEmpty()) Parameter.DEFAULT_LOAD_URL.value else mMainViewModel.getHost()

        val stringRequest = object : JsonObjectRequest(
            Method.GET, "$url/$id", null,
            { response ->
                try {
                    val dataObject = response.getJSONObject(Parameter.DATA_PARAM.value)

                    val ad = AdEntity(dataObject.getInt("id"), dataObject.getString("phone"), 0, dataObject.getString("device"), dataObject.getJSONObject("city").getString("name"), dataObject.getJSONObject("classification").getString("name"), dataObject.getJSONObject("status").getInt("id"))

                    val items = arrayOf("# " + ad.id.toString(), getString(R.string.city) + ": " + ad.city, getString(R.string.classification) + ": " + ad.classification, getString(R.string.phone) + ": " + ad.formatPhone())

                    MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.data)
                        .setItems(items){ _, _ ->
                        }
                        .setPositiveButton(R.string.accept) { dialog, _ ->
                            ad.app = appCode
                            mMainViewModel.saveAd(ad)
                            dialog.dismiss()
                        }
                        .setNegativeButton(R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setCancelable(Constants.HIDE)
                        .show()

                }catch (e: Exception){
                    println(e.toString())
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            {   volleyError ->
                println(volleyError.toString())
            }){}

        stringRequest .retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )

        Application.volleyAPI.addToRequestQueue(stringRequest)
    }

    private fun registerPhone(record: RecordEntity){
        val url = if(mMainViewModel.getHost().isNullOrEmpty()) Parameter.DEFAULT_LOAD_URL.value else mMainViewModel.getHost()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    println(response)
                    val jsonObject = JSONObject(response)
                    val dataObject = jsonObject.getJSONObject(Parameter.DATA_PARAM.value)
                    val message = dataObject.getString("message")

                    if(message == "Guardado")
                        mMainViewModel.updateRecord(record)
                }catch (e: Exception){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            {   volleyError ->
                println(volleyError.toString())
            }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params[Parameter.AD_PARAM.value] = record.id_ad.toString()
                params[Parameter.PHONE_PARAM.value] = record.phone
                return params
            }
        }

        stringRequest .retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )

        Application.volleyAPI.addToRequestQueue(stringRequest)
    }

    fun showRecyclerView(flag: Boolean, btn: Boolean){
        mBinding.recycleView.visibility = if(flag) View.VISIBLE else View.GONE
        mBinding.btnAdd.visibility = if(btn) View.VISIBLE else View.GONE
    }

    //Listener Ad
    override fun onInformation(ad: AdEntity) {
        launchAdDataFragment(ad)
    }

    override fun onDelete(ad: AdEntity) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.ad)
            .setMessage(R.string.you_sure_remove)
            .setPositiveButton(R.string.accept){ dialog, _ ->
                mMainViewModel.removeAd(ad)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel){    dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(Constants.HIDE)
            .show()
    }
}