package com.smart.notification.dataAdModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.notification.R
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.entities.RecordEntity
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.getMsgErrorByCode
import com.smart.notification.model.ModelRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Project: NotificationApp
 * Package: com.smart.notification.dataAdModule.viewModel
 * Update by israel on Tuesday, 7/12/2022 3:44 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class DataAdViewModel: ViewModel() {
    private var idAd: Int = 0
    private val repository = ModelRepository()
    private val snackBarMsg = MutableLiveData<Int>()
    private val progressBar = MutableLiveData<Boolean>()
    private val showButtonAdd = MutableLiveData<Boolean>()

    fun setAdSelect(ad: AdEntity){
        idAd = ad.id
    }

    fun setShowButtonAdd(status: Boolean){
        showButtonAdd.value = status
    }

    fun getDevice() = repository.getDevice()
    fun getAdSelect() = repository.getAdById(idAd)
    fun getShowButtonAdd() = showButtonAdd
    fun getRecordCount() = repository.getRecordCountByAd(idAd)
    fun getRecordAll() = repository.getRecordByAd(idAd)
    fun getSnackBarMsg() = snackBarMsg
    fun getProgressBar() = progressBar

    fun saveRecord(record: RecordEntity){
        executeAction(R.string.phone_saved) {
            repository.saveRecord(record)
        }
    }

    fun updateRecord(record: RecordEntity){
        executeAction(R.string.phone_updated) {
            repository.updateRecord(record)
        }
    }

    fun removeRecordAd(id_ad: Int = idAd){
        executeAction(R.string.record_removed){
            repository.removeRecordAd(id_ad)
        }
    }

    private fun executeAction(msgCode: Int, block: suspend () -> Unit): Job {
        return viewModelScope.launch {
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