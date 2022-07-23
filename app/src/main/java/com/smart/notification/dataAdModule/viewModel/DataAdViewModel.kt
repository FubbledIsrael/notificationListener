package com.smart.notification.dataAdModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.notification.common.entities.AdEntity
import com.smart.notification.common.utils.Constants
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
    private val progressBar = MutableLiveData<Boolean>()
    private var showButtonAdd : Boolean = false

    fun setAdSelect(ad: AdEntity){
        idAd = ad.id
    }

    fun setShowButtonAdd(status: Boolean){
        showButtonAdd = status
    }

    fun getDevice() = repository.getDevice()
    fun getAdSelect() = repository.getAdById(idAd)
    fun getShowButtonAdd() = showButtonAdd
    fun getRecordCount() = repository.getRecordCountByAd(idAd)
    fun getRecordAll() = repository.getRecordByAd(idAd)
    fun getProgressBar() = progressBar

    fun removeRecordAd(){
        executeAction{
            repository.removeRecordAd(idAd)
            repository.removeLastTime()
        }
    }

    private fun executeAction(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
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