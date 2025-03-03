package com.anp.mobiletopup

import android.app.Application
import androidx.room.Room
import com.anp.mobiletopup.db.HistoryDataBase

class MainApplication : Application() {
    companion object{
        lateinit var historyDataBase: HistoryDataBase
    }
    override fun onCreate(){
        super.onCreate()
        historyDataBase =  Room.databaseBuilder(
            applicationContext,
            HistoryDataBase::class.java,
            HistoryDataBase.NAME
        ).build()


    }
}