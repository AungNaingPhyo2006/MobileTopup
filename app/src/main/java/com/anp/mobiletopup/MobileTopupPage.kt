package com.anp.mobiletopup

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss")
    return current.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MobileTopup(navController: NavController,modifier: Modifier = Modifier , viewModel: TodoViewModel) {
    val phoneNumberUtil = MyanmarPhoneNumberUtil()
    val formattedDateTime = getCurrentDateTime()
    val topupOptions = listOf(
        "Air Time Topup" to 1000,
        "Air Time Topup" to 2000,
        "Air Time Topup" to 3000,
        "Air Time Topup" to 5000,
        "Air Time Topup" to 10000,
        "Air Time Topup" to 20000
    )
    val dataOptions = listOf(
        "Shal Plan (1212 MB)" to 999,
        "Shal Plan (2024 MB)" to 1999,
        "Shal Plan (3072 MB)" to 2999,
        "Shal Plan (5120 MB)" to 3999,
        "Shal Plan (10240 MB)" to 5999
    )

    val remainingBalance = viewModel.remainingBalance
    val rechargeNumber = viewModel.rechargeNumber
    val phoneNumber = remember { mutableStateOf(TextFieldValue(rechargeNumber.value ?: "")) }
    val rechargeOperator = viewModel.rechargeOperator
    val operatorName = remember { mutableStateOf(rechargeOperator.value ?: "")}

    val selectedTopupOption = remember { mutableStateOf<Pair<String, Int>?>(null) }
    val selectedDataOption = remember { mutableStateOf<Pair<String, Int>?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    var alertMessage = remember { mutableStateOf("")}

    Column (modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text( text = "Mobile Topup",fontSize = 24.sp, color = Color.DarkGray,
            fontWeight = FontWeight.Bold)
        Text("Total Amount: ${remainingBalance.value}  MMK", fontSize = 12.sp, color = Color.Magenta)
        TextField(
            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
            value = phoneNumber.value,
            onValueChange = {
                phoneNumber.value = it
                if (it.text.isNotEmpty()) {
                    alertMessage.value = ""
                    operatorName.value = phoneNumberUtil.getTelecomName(it.text)
                } else {
                    operatorName.value = ""

                }
            },
            label = {Text("Enter Phone Number")},
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Gray,
                errorContainerColor = Color.Red,
            ),
        )
        if (operatorName.value.isNotEmpty()) {
            Text(text="Operator: ${operatorName.value}",
                fontSize = 11.sp, color = Color.Black,fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text( text = "Air Time Topup",fontSize = 18.sp, color = Color.Black,fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )
        topupOptions.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (packageName, price) ->
                    val isSelected = selectedTopupOption.value == (packageName to price)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(if (isSelected) Color.Blue else Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .clickable() {
                                selectedTopupOption.value = packageName to price
                                if(selectedTopupOption.value != null) {
                                    showDialog.value = true
                                }
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$price MMK", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.DarkGray)
                        }
                    }
                }
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text( text = "Data Plan",fontSize = 18.sp, color = Color.Black,fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp))
        dataOptions.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (packageName, price) ->
                    val isSelected = selectedDataOption.value == (packageName to price)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(  if (isSelected) Color.Blue else Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .clickable() { selectedDataOption.value = packageName to price
                                selectedTopupOption.value = null
                                if(selectedDataOption.value !=null) {
                                    showDialog.value = true
                                }
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Text(text = packageName, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Blue,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp))
                            Text(text = "$price MMK", fontSize = 11.sp,
                                color = if (isSelected) Color.White else Color.DarkGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp))
                        }
                    }
                }
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Modal Dialog
        if (showDialog.value) {

            val topupOption = selectedTopupOption.value
            val dataOption = selectedDataOption.value

            // Use topup option if available, otherwise use data option
            val packageName = topupOption?.first ?: dataOption?.first ?: "Unknown"
            val price = topupOption?.second ?: dataOption?.second ?: "0"

            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {  Text(
                    text = "Confirm Purchase",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6200EE)
                ) },
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if(alertMessage.value.isNotEmpty()){
                            Text(
                                text = alertMessage.value,
                                fontSize = 16.sp,
                                color = Color.Red,
                                fontWeight = FontWeight.Medium
                            )
                        }else{
                            if(operatorName.value.isNotEmpty() && operatorName.value != "Unknown"  || rechargeNumber.value.isNotEmpty()){
                                Row {
                                    Text(
                                        text = "Operator Name: ",
                                        color = Color.Gray ,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = operatorName.value,
                                        color = Color.Blue
                                    )

                                }
                                Row {
                                    Text(
                                        text = "Phone number: ",
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = phoneNumber.value.text,
                                        color = Color.Blue
                                    )

                                }
                                Row {
                                    Text(
                                        text = "Package Name: ",
                                        color = Color.Gray ,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = packageName,
                                        color = Color.Blue
                                    )

                                }
                                Row {
                                    Text(
                                        text = "Package Price: ",
                                        color = Color.Gray ,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = price.toString() + "MMK",
                                        color = Color.Blue
                                    )

                                }
                                Text(
                                    text = "Purchase Date: $formattedDateTime",
                                    color = Color.Blue,
                                    fontSize = 11.sp,
                                )
                            }else{
                                Text(
                                    text = "Please Enter Valid Number!",
                                    fontSize = 16.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Medium
                                )
                            }


                        }

                    }

                },
                confirmButton = {
                    TextButton(onClick = {
                        val purchasePrice = topupOption?.second ?: dataOption?.second ?: 0
                        if (remainingBalance.value >= purchasePrice){
                            if(operatorName.value.isNotEmpty() && operatorName.value != "Unknown" ){
                                remainingBalance.value -= purchasePrice
                                viewModel.addTodo(
                                    packageName = packageName,
                                    operatorName = operatorName.value,
                                    price = price.toString(),
                                    phoneNumber = phoneNumber.value.text
                                )
                                alertMessage.value = ""
                                showDialog.value = false
                                navController.navigate(Routes.successScreen + "/${packageName}/${price}/${operatorName.value}/${phoneNumber.value.text}")
                            }else{
                                if( phoneNumber.value.text.isEmpty() ||operatorName.value == "Unknown" || rechargeNumber.value.isEmpty()){
                                    alertMessage.value = "Please Enter Valid Number!"
                                }
                            }
                        }else{
                            alertMessage.value = "Insufficient balance!"
                        }


                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog.value = false
                        selectedTopupOption.value = null
                        selectedDataOption.value = null
                        alertMessage.value = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        Button(onClick = {
            navController.navigate(Routes.topupHistory)
        }) {
            Text( text = "History")
        }
    }
}