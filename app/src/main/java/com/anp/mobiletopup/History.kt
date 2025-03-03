package com.anp.mobiletopup

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

//@Entity
data class History(
//    @PrimaryKey(autoGenerate = true)
//    var id : Int = 0,
    var id : Int,
    var packageName : String,
    var operatorName : String,
    var price : String,
    var phoneNumber : String,
    var createdAt : Date
)

@RequiresApi(Build.VERSION_CODES.O)
fun getFakeHistory(): List<History>{
    return listOf<History>(
        History(1 ,  "Bill", "MPT","2000","09425321801",Date.from(Instant.now())),
        History(2 ,  "Package", "Atom","4000","09425321801",Date.from(Instant.now())),
        History(3 ,  "Bill", "Mytel","5000","09425321801",Date.from(Instant.now()))
    )
}
