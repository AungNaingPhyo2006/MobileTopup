package com.anp.mobiletopup

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            title = { Text("Details", fontWeight = FontWeight.Bold) },
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

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Detail",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            details?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailItem(label = "Operator Name", value = it.operatorName)
                        DetailItem(label = "Package Name", value = it.packageName)
                        DetailItem(label = "Purchase Price", value = it.price)
                        DetailItem(label = "Phone Number", value = it.phoneNumber)
                        Text(
                            text = SimpleDateFormat("hh:mm a , dd/MM/yy", Locale.ENGLISH).format(it.createdAt),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            } ?: Text(
                text = "Loading...",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.mobileTopup)
                    rechargeNumber.value = phoneNumber.toString()
                    rechargeOperator.value = operator.toString()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Recharge", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}