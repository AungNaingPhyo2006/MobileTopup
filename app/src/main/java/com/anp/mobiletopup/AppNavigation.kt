package com.anp.mobiletopup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anp.mobiletopup.ui.theme.MobileTopupTheme

@Composable
fun AppNavigation(historyViewModel: HistoryViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.mobileTopup, builder = {
        composable (Routes.mobileTopup){
            MobileTopupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MobileTopup(
                        navController,modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        composable (Routes.topupHistory){
            MobileTopupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopupHistory(
                       historyViewModel, navController, modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        composable (Routes.successScreen + "/{packageName}/{price}/{operatorName}/{phoneNumber}"){
            MobileTopupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SuccessScreen(
                        navController, modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    })
}