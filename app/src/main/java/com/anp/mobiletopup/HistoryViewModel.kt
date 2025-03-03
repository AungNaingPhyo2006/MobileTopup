package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    val historyDao = MainApplication.historyDataBase.getHistoryDao()
    var historyList : LiveData<List<History>> = historyDao.getHistory()


    @RequiresApi(Build.VERSION_CODES.O)
    fun addHistory(
        packageName : String,
        operatorName : String,
        price : String,
        phoneNumber : String,
    ){
        viewModelScope.launch(Dispatchers.IO){
            historyDao.addHistory(History(packageName,operatorName,price,phoneNumber))
        }

    }
}