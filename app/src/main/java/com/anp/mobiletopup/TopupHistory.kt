package com.anp.mobiletopup

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TopupHistory( navController: NavController, modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        Text( text = "Topup History Screen")
        Button(onClick = {
            navController.navigate(Routes.mobileTopup)
        }) {
            Text( text = "To Mobile Topup")
        }
    }
}