package com.anp.mobiletopup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MobileTopup(navController: NavController,modifier: Modifier = Modifier) {
    val phoneNumber = remember { mutableStateOf(TextFieldValue())}
    val topUpOptions = listOf(1000, 2000, 3000, 5000, 10000,20000)
    val initAmount = 15000
//    var selectedAmount by remember { mutableStateOf(0) }

//    var totalBalance =  initAmount - selectedAmount

    Column (modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text( text = "Mobile Topup",fontSize = 24.sp, color = Color.Black)
        Text("Total Amount: ${initAmount} MMK", fontSize = 12.sp, color = Color.Black)
        TextField(
            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
            value = phoneNumber.value,
            onValueChange = {phoneNumber.value = it},
            label = {Text("Enter Phone Number")},
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )


        topUpOptions.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { amount ->
                    Box(
                        modifier = Modifier
                            .weight(1f) // Distributes space equally
                            .padding(8.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .clickable {  }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$amount MMK", fontSize = 11.sp)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate(Routes.topupHistory)
        }) {
            Text( text = "To History")
        }
    }
}