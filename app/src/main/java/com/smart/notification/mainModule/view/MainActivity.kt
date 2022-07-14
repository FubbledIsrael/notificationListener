package com.smart.notification.mainModule.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.smart.notification.R
import com.smart.notification.databinding.ActivityMainBinding
import com.smart.notification.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.smart.notification.Application
import com.smart.notification.common.adapters.AdListAdapter
import com.smart.notification.common.adapters.OnClickListenerAd
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.service.AdDialog
import com.smart.notification.common.service.DeviceDialog
import com.smart.notification.common.utils.Constants
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
    private lateinit var mGridLayout: GridLayoutManager


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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mDataAdViewModel.setShowButtonAdd(Constants.SHOW)
    }

    //Setup
    private fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mDataAdViewModel = ViewModelProvider(this)[DataAdViewModel::class.java]

        mAdapter = AdListAdapter(this)
        mGridLayout = GridLayoutManager(this, resources.getInteger(R.integer.counter_min_line))
        mBinding.recycleView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun setupObservers() {
        mMainViewModel.getAdAll().observe(this){    adList ->
            mBinding.progressBar.visibility = View.GONE
            mBinding.tvMessage.visibility = if(adList.size > 0) View.GONE else View.VISIBLE

            mAdapter.submitList(adList)
        }

        mMainViewModel.getAdCount().observe(this){  total ->
            mBinding.btnAdd.visibility = if(total == 2) View.GONE else View.VISIBLE
        }

        mMainViewModel.getSnackBarMsg().observe(this){      message ->
            Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
        }

        mMainViewModel.getProgressBar().observe(this){      flag ->
            mBinding.progressBar.visibility = if(flag) View.VISIBLE else View.GONE
        }

        mDataAdViewModel.getShowButtonAdd().observe(this) {     flag ->
            mBinding.btnAdd.visibility = if(flag) View.VISIBLE else View.GONE
        }
    }

    private fun setupButtons() {
        mBinding.btnAdd.setOnClickListener {
            AdDialog(
                onSubmitClickListener = {   ad ->
                    mMainViewModel.setProgressBar(Constants.SHOW)
                    getAd(ad.id.toString(), ad.app)
                }
            ).show(supportFragmentManager, Constants.AD_PARAM)
        }
    }

    //Setup Menu Options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ad, menu)
        menu!!.findItem(R.id.action_clean).isVisible = Constants.HIDE
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit -> {
                DeviceDialog(
                    device = mMainViewModel.getDevice(),
                    onSubmitClickListener = {   name ->
                        mMainViewModel.updateDevice(name)
                        supportActionBar?.title = name
                    }
                ).show(supportFragmentManager, Constants.DEVICE_PARAM)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Verify Notification Service
    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = this.contentResolver
        val enabledNotificationListeners = Settings.Secure.getString(contentResolver, Constants.ENABLED_NOTIFICATION_LISTENERS)
        val packageName = this.packageName

        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName))
    }

    //Alert Dialog
    private fun setupNotificationSnack(){
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.notification_listener_service)
            .setMessage(R.string.notification_listener_service_explanation)
            .setPositiveButton(R.string.accept){ dialog, _ ->
                startActivity(Intent(Constants.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                dialog.dismiss()
            }
            .setCancelable(false)
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
                ).show(supportFragmentManager, Constants.DEVICE_PARAM)
            }
    }

    private fun launchAdDataFragment(ad: AdEntity){
        mDataAdViewModel.setShowButtonAdd(Constants.HIDE)
        mDataAdViewModel.setAdSelect(ad)

        val fragment = DataAdFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //Load Ad on Server
    private fun getAd(id: String, appCode: Int){
        val url = Constants.BASE_URL + Constants.CONTROLLER_PATH + Constants.AD_PATH_FILE

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                mMainViewModel.setProgressBar(Constants.HIDE)
                try {
                    val jsonObject = JSONObject(response)
                    val error = jsonObject.optInt(Constants.ERROR_PARAM)

                    if(error == 0){
                        val dataJson = jsonObject.optString(Constants.DATA_PARAM)
                        val ad = Gson().fromJson(dataJson , AdEntity::class.java)

                        val items = arrayOf("# " + ad.id.toString(), getString(R.string.city) + ": " + ad.city, getString(R.string.classification) + ": " + ad.classification, getString(R.string.phone) + ": " + ad.formatPhone())

                        MaterialAlertDialogBuilder(this)
                            .setTitle(getString(R.string.data))
                            .setItems(items){ _, _ ->
                            }
                            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                                ad.app = appCode
                                mMainViewModel.saveAd(ad)
                                dialog.dismiss()
                            }
                            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                    }
                    else
                        Toast.makeText(this, R.string.error_id_invalid, Toast.LENGTH_SHORT).show()
                }catch (e: Exception){
                    println(e.message)
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            {   volleyError ->
                volleyError.printStackTrace()
                println(volleyError.message)
                Toast.makeText(this, getString(R.string.warning) + ": " + volleyError.message, Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params[Constants.FUNCTION_PARAM] = Constants.GET_ID_PARAM
                params[Constants.ID_PARAM] = id
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

    //Listener Ad
    override fun onClick(ad: AdEntity) {
        launchAdDataFragment(ad)
    }

    override fun onClickLong(ad: AdEntity) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.ad)
            .setMessage(R.string.you_sure_remove)
            .setPositiveButton(R.string.accept){ dialog, _ ->
                mMainViewModel.removeAd(ad)
                mDataAdViewModel.removeRecordAd(ad.id)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel){    dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}