package com.anp.mobiletopup

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var packageName : String,
    var operatorName : String,
    var price : String,
    var phoneNumber : String,
    var createdAt : Date
)