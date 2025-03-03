package com.anp.mobiletopup

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var packageName : String,
    var operatorName : String,
    var price : String,
    var phoneNumber : String,
    var createdAt : Date
)


