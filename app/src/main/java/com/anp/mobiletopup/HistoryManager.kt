package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.time.Instant
import java.util.Date

object HistoryManager {
    private val historyList = mutableListOf<History>()

    fun getAllHistory() : List<History>{
     return historyList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addHistory(
         packageName : String,
         operatorName : String,
         price : String,
         phoneNumber : String,
    ){
     historyList.add(History(System.currentTimeMillis().toInt(),packageName,operatorName,price,phoneNumber, Date.from(
         Instant.now())))
    }
}