package com.smart.notification.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.notification.R
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.getMsgErrorByCode
import com.smart.notification.model.ModelRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Project: NotificationApp
 * Package: com.smart.notification.mainModule.viewModel
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class MainViewModel: ViewModel() {
    private val repository = ModelRepository()
    private val snackBarMsg = MutableLiveData<Int>()
    private val progressBar = MutableLiveData<Boolean>()

    fun setProgressBar(flag: Boolean){
        progressBar.value = flag
    }

    fun getDevice() = repository.getDevice()
    fun getAdAll() = repository.getAdAll()
    fun getAdCount() = repository.getAdCount()
    fun getSnackBarMsg() = snackBarMsg
    fun getProgressBar() = progressBar

    fun saveDevice(device: String){
        executeAction(R.string.device_saved) {
            repository.saveDevice(device)
        }
    }

    fun updateDevice(device: String){
        executeAction(R.string.device_updated) {
            repository.saveDevice(device)
        }
    }

    fun saveAd(ad: AdEntity){
        executeAction(R.string.ad_saved){
            repository.saveAd(ad)
        }
    }

    fun removeAd(ad: AdEntity){
        executeAction(R.string.ad_removed){
            repository.removeAd(ad)
        }
    }

    private fun executeAction(msgCode: Int, block: suspend () -> Unit): Job {
        return  viewModelScope.launch {
            progressBar.value = Constants.SHOW
            try {
                block()
                snackBarMsg.value = msgCode
            } catch (e: Exception){
                snackBarMsg.value = getMsgErrorByCode(e.message)
            } finally {
                progressBar.value = Constants.HIDE
            }
        }
    }
}