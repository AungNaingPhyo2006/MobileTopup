package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {
    private var _historyList = MutableLiveData<List<History>>()
    val historyList : LiveData<List<History>> = _historyList

    fun getAllHistory(): List<History> {
        return historyList.value = HistoryManager.getAllHistory()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addHistory(
        packageName : String,
        operatorName : String,
        price : String,
        phoneNumber : String,
    ){
        HistoryManager.addHistory(packageName,operatorName,price,phoneNumber)
        getAllHistory()
    }
}