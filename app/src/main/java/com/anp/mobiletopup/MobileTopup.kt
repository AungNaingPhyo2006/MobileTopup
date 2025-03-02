package com.anp.mobiletopup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MobileTopup(navController: NavController,modifier: Modifier = Modifier) {
    Column (modifier = modifier) {
        Text( text = "Mobile Topup Screen")
        Button(onClick = {
            navController.navigate(Routes.topupHistory)
        }) {
            Text( text = "To History")
        }
    }
}