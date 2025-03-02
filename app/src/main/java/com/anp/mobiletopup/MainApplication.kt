//package com.anp.mobiletopup
//
//import android.app.Application
//import androidx.room.Room
//import com.anp.mobiletopup.db.HistoryDataBase
//
//class MainApplication : Application() {
//    companion object{
//        lateinit var historyDatabase: HistoryDataBase
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        historyDatabase = Room.databaseBuilder(
//            applicationContext,
//            HistoryDataBase::class.java,
//            HistoryDataBase.NAME
//        ).build()
//    }
//}