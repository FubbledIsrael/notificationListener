package com.example.samsmx.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.example.samsmx.R
import com.example.samsmx.`class`.Ad
import com.example.samsmx.`class`.RegisterPhone
import com.example.samsmx.`class`.ResponseAd
import com.example.samsmx.`class`.SettingsApplication.Companion.storage
import com.example.samsmx.`class`.VolleySingleton
import com.example.samsmx.databinding.ActivityMainBinding
import com.example.samsmx.enum.KeyAd
import com.example.samsmx.enum.KeyHosting
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val objectAd: List<Ad> = Gson().fromJson(storage.getValueString(KeyAd.DataJson.value), Array<Ad>::class.java).toList()
            if(objectAd.isNotEmpty()) {
                startActivity(Intent(this, PhoneNumberActivity::class.java))
                finish()
            }
        }catch (e: Exception){ }

        binding.btnEnter.setOnClickListener {
            when{
                binding.etNameDevice.text.isNullOrEmpty() -> {
                    binding.layoutNameDevice.error = getString(R.string.enter_name_device)
                    return@setOnClickListener
                }
                binding.etId.text.isNullOrEmpty() -> {
                    binding.layoutInputId.error = getString(R.string.enter_id_ad)
                    return@setOnClickListener
                }
                binding.etId.text.toString().equals(binding.etSecondId.text.toString(), true) -> {
                    binding.layoutInputId.error = getString(R.string.enter_different_id)
                    return@setOnClickListener
                }
                else -> {
                    binding.layoutInputId.error = null
                    val nameDevice = binding.etNameDevice.text.toString()
                    val id = binding.etId.text.toString()
                    val secondId = if(binding.etSecondId.text.isNullOrEmpty()) null else binding.etSecondId.text.toString()

                    getAd(nameDevice, id, secondId)
                }
            }
        }

        binding.btnAddAd.setOnClickListener {

            if(binding.layoutInputSecondId.isVisible) {
                binding.etSecondId.text = null
                binding.layoutInputSecondId.visibility = View.GONE
                binding.btnAddAd.text = getString(R.string.add_id)
            }
            else {
                binding.layoutInputSecondId.visibility = View.VISIBLE
                binding.btnAddAd.text = getString(R.string.remove_id)
                }
        }
    }

    private fun getAd(nameDevice: String, id: String, secondId: String?){
        val argument = arrayOfNulls<String>(2)
        argument[0] = id
        argument[1] = secondId

        val careType = JSONArray()
        for (i in argument.indices)
            careType.put(argument[i])

        val stringRequest = object : StringRequest(
            Method.POST, KeyHosting.UrlAd.url,
            { response ->
                try {
                    val jsonResponse = Gson().fromJson(response , ResponseAd::class.java)
                    if(jsonResponse.error == 0){
                        when (jsonResponse.countData) {
                            2 -> {
                                if(jsonResponse.data.filterNotNull().isNotEmpty()) {
                                    var items1: Array<String> = arrayOf()
                                    var message1: String? = null
                                    if (jsonResponse.data[0] != null)
                                        items1 = arrayOf(
                                            getString(R.string.id) + ": " + jsonResponse.data[0]?.id,
                                            getString(R.string.city) + ": " + jsonResponse.data[0]?.city,
                                            getString(R.string.category) + ": " + jsonResponse.data[0]?.category,
                                            getString(R.string.phoneMile) + ": " + jsonResponse.data[0]?.phoneMile
                                        )
                                    else
                                        message1 = getString(R.string.error_invalid_ad)

                                    MaterialAlertDialogBuilder(this)
                                        .setTitle(getString(R.string.ad_data))
                                        .setMessage(message1)
                                        .setItems(items1) { _, _ ->
                                        }
                                        .setPositiveButton(resources.getString(R.string.next)) { dialog1, _ ->
                                            dialog1.dismiss()

                                            var items2: Array<String> = arrayOf()
                                            var message2: String? = null
                                            if (jsonResponse.data[1] != null)
                                                items2 = arrayOf(
                                                    getString(R.string.id) + ": " + jsonResponse.data[1]?.id,
                                                    getString(R.string.city) + ": " + jsonResponse.data[1]?.city,
                                                    getString(R.string.category) + ": " + jsonResponse.data[1]?.category,
                                                    getString(R.string.phoneMile) + ": " + jsonResponse.data[1]?.phoneMile
                                                )
                                            else
                                                message2 = getString(R.string.error_invalid_ad)

                                            MaterialAlertDialogBuilder(this)
                                                .setTitle(getString(R.string.ad_data))
                                                .setMessage(message2)
                                                .setItems(items2) { _, _ ->
                                                }
                                                .setPositiveButton(resources.getString(R.string.next)) { dialog2, _ ->
                                                    dialog2.dismiss()

                                                    MaterialAlertDialogBuilder(this)
                                                        .setTitle(getString(R.string.ad_data))
                                                        .setMessage(getString(R.string.number_ads) + ": " + jsonResponse.data.filterNotNull().size)
                                                        .setNegativeButton(resources.getString(R.string.cancel)) { dialog3, _ ->
                                                            dialog3.dismiss()
                                                        }
                                                        .setPositiveButton(resources.getString(R.string.accept)) { dialog3, _ ->
                                                            val jsonRegisterAd = listOf(RegisterPhone(getString(R.string.waiting), 0), RegisterPhone(getString(R.string.waiting), 0))
                                                            savePreferences(nameDevice, jsonResponse.data.filterNotNull(), jsonRegisterAd)
                                                            startActivity(Intent(this, PhoneNumberActivity::class.java))
                                                            finish()
                                                            dialog3.dismiss()
                                                        }
                                                        .show()
                                                }
                                                .setCancelable(false)
                                                .show()
                                        }
                                        .setCancelable(false)
                                        .show()
                                }
                                else
                                    Snackbar.make(binding.linearlayout, R.string.error_invalid_ad ,Snackbar.LENGTH_SHORT).show()
                            }
                            1 -> {
                                if(jsonResponse.data.filterNotNull().isNotEmpty()){
                                    val items = arrayOf(getString(R.string.id) + ": " + jsonResponse.data[0]?.id, getString(R.string.city) + ": " + jsonResponse.data[0]?.city, getString(R.string.category) + ": " + jsonResponse.data[0]?.category, getString(R.string.phoneMile) + ": " + jsonResponse.data[0]?.phoneMile)

                                    MaterialAlertDialogBuilder(this)
                                        .setTitle(getString(R.string.ad_data))
                                        .setItems(items){ _, _ ->
                                        }
                                        .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                                            dialog.dismiss()
                                        }
                                        .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                                            val jsonRegisterAd = listOf(RegisterPhone(getString(R.string.waiting), 0))
                                            savePreferences(nameDevice, jsonResponse.data.filterNotNull(), jsonRegisterAd)
                                            startActivity(Intent(this, PhoneNumberActivity::class.java))
                                            finish()
                                            dialog.dismiss()
                                        }
                                        .setCancelable(false)
                                        .show()
                                }else
                                    Snackbar.make(binding.linearlayout, R.string.error_invalid_ad ,Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }catch (e: Exception){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                }
            },
            {
                Toast.makeText(this, getString(R.string.warning) + ": " + it.message, Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["function"] = "getIds"
                params["id"] = careType.toString()
                return params
            }
        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

    private fun savePreferences(nameDevice: String, ad: List<Ad>, register: List<RegisterPhone>){
        val jsonDataAd = Gson().toJson(ad)
        val jsonRegisterAd = Gson().toJson(register)
        storage.setValue(KeyAd.DeviceName.value, nameDevice)
        storage.setValue(KeyAd.DataJson.value, jsonDataAd)
        storage.setValue(KeyAd.RegisterJson.value, jsonRegisterAd)
    }
}