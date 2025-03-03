package com.anp.mobiletopup.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anp.mobiletopup.History

@Database(entities = [History::class], version = 1)
@TypeConverters(Converters::class)
 abstract  class HistoryDataBase  : RoomDatabase(){
     companion object{
         const val NAME = "History_DB"
     }
    abstract fun getHistoryDao() : HistoryDao
}