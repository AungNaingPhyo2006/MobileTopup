package com.anp.mobiletopup

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessPage(navController: NavController, modifier: Modifier = Modifier,viewModel: TodoViewModel) {
    val packageName = navController.currentBackStackEntry?.arguments?.getString("packageName") ?: "Unknown"
    val price = navController.currentBackStackEntry?.arguments?.getString("price") ?: "0"
    val operatorName = navController.currentBackStackEntry?.arguments?.getString("operatorName") ?: "Unknown"
    val phoneNumber = navController.currentBackStackEntry?.arguments?.getString("phoneNumber") ?: "Unknown"
    val rechargeNumber = viewModel.rechargeNumber
    val rechargeOperator = viewModel.rechargeOperator

    Column(modifier = Modifier) {
        TopAppBar(
            title = { Text("Success") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
            )
        )

        Column(modifier = Modifier.padding(16.dp)
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                contentDescription = "Success",
                tint = Color.Green,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Success",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
            DetailRow(label = "Operator Name", value = operatorName)
            DetailRow(label = "Phone Number", value = phoneNumber)
            DetailRow(label = "Package", value = packageName)
            DetailRow(label = "Price", value = "$price MMK")
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate(Routes.mobileTopup)
                    rechargeNumber.value = ""
                    rechargeOperator.value = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "အဆင်ပြေသည်", fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}