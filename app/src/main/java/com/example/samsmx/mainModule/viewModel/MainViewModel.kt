package com.example.samsmx.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsmx.R
import com.example.samsmx.common.entitie.DeviceEntity
import com.example.samsmx.common.utils.Constants
import com.example.samsmx.common.utils.getMsgErrorByCode
import com.example.samsmx.mainModule.model.MainRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Project: samsMx
 * Package: com.example.samsmx.mainModule.viewModel
 * Update by israe on Thursday, 5/19/2022 6:42 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class MainViewModel: ViewModel() {
    private val repository = MainRepository()
    private val snackBarMsg = MutableLiveData<Int>()
    private val hideKeyboard = MutableLiveData<Boolean>()

    fun getSnackBarMsg() = snackBarMsg
    fun isHideKeyboard() = hideKeyboard
    fun getDevice() = repository.getDevice()

    fun addDevice(device: DeviceEntity){
        executeAction(){
            repository.saveDevice(device)
        }
    }

    fun updateDevice(device: DeviceEntity){
        executeAction(){
            repository.updateDevice(device)
        }
    }

    private fun executeAction(block: suspend () -> Unit): Job {
        return  viewModelScope.launch {
            hideKeyboard.value = Constants.SHOW
            try {
                block()
                snackBarMsg.value = R.string.updated
            } catch (e: Exception){
                snackBarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }
}