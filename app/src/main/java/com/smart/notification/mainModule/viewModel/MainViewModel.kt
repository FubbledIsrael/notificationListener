package com.smart.notification.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.entities.RecordEntity
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.getPackageByCode
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
    private val progressBar = MutableLiveData<Boolean>()

    fun setProgressBar(flag: Boolean){
        progressBar.value = flag
    }

    fun getDevice() = repository.getDevice()
    fun getApplication(key: String) = repository.getApplication(key)
    fun getNotificationByPackage(id: Int): Int = repository.getNotificationByPackage(id)
    fun getAdAll() = repository.getAdAll()
    fun getAdCount() = repository.getAdCount()
    fun getRecordByStatus(status: Int) = repository.getRecordByStatus(status)
    fun getProgressBar() = progressBar

    fun saveDevice(device: String){
        executeAction{
            repository.saveDevice(device)
        }
    }

    fun updateDevice(device: String){
        executeAction{
            repository.saveDevice(device)
        }
    }

    fun saveAd(ad: AdEntity){
        executeAction{
            repository.saveAd(ad)
            repository.saveApplication(getPackageByCode(ad.app).value, ad.id)
        }
    }

    fun updateAd(ad: AdEntity){
        executeAction{
            repository.updateAd(ad)
        }
    }

    fun removeAd(ad: AdEntity){
        executeAction{
            repository.removeAd(ad)
            repository.removeApplication(getPackageByCode(ad.app).value)
            repository.removeRecordAd(ad.id)
        }
    }

    fun saveRecord(record: RecordEntity){
        executeAction {
            repository.saveRecord(record)
        }
    }

    private fun executeAction(block: suspend () -> Unit): Job {
        return  viewModelScope.launch {
            progressBar.value = Constants.SHOW
            try {
                block()
            } catch (e: Exception){
                println(e.message)
            } finally {
                progressBar.value = Constants.HIDE
            }
        }
    }
}