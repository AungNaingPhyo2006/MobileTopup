package com.anp.mobiletopup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavController, modifier: Modifier, viewModel: TodoViewModel) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")?.toIntOrNull()
    val details by id?.let { viewModel.getTodoById(it).observeAsState() } ?: remember { mutableStateOf(null) }
    val rechargeNumber = viewModel.rechargeNumber
    val rechargeOperator = viewModel.rechargeOperator


    val phoneNumber = details?.phoneNumber
    val operator = details?.operatorName

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Details") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Detail", fontSize = 18.sp)

            details?.let {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(text = "Operator Name: ${it.operatorName}", fontSize = 16.sp,)
                    Text(text = "Package Name: ${it.packageName}", fontSize = 16.sp)
                    Text(text = "Purchase Price: ${it.price}", fontSize = 16.sp)
                    Text(text = "Phone Number: ${it.phoneNumber}", fontSize = 16.sp)
//                    Text(text = "Purchase Date: ${it.createdAt}", fontSize = 16.sp)
                    Text(text = SimpleDateFormat("hh:mm a , dd/MM/yy", Locale.ENGLISH).format(it.createdAt)
                        ,fontSize=12.sp,color = Color.Gray
                    )
                }
            } ?: Text(text = "Loading...", fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                navController.navigate(Routes.mobileTopup)
                rechargeNumber.value = phoneNumber.toString()
                rechargeOperator.value = operator.toString()
            }) {
                Text(text = "Recharge")
            }
        }
    }
}
