package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopupHistory(navController: NavController, modifier: Modifier = Modifier, viewModel: HistoryViewModel) {

    val historyList by viewModel.historyList.observeAsState()

    Column(modifier = Modifier) {
        // TopAppBar with back arrow and title
        TopAppBar(
            title = { Text("Topup History") },
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

        // Rest of the content
        Column(modifier = Modifier.padding(16.dp)) {

           historyList?.let{
               LazyColumn(
                   content = {
                       itemsIndexed(it){ index: Int, item : History->
                           HistoryItem(item = item)
                       }
                   }
               )
           }

            Button(onClick = {
                navController.navigate(Routes.mobileTopup)
            }) {
                Text(text = "To Mobile Topup")
            }
        }
    }
}

@Composable
fun HistoryItem (item : History){
    Row(modifier = Modifier.fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.Cyan)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically)  {
        Column (modifier = Modifier.weight(1f)) {
            Text(text = item.operatorName, fontSize=18.sp, color = Color.White)
            Text(text = item.phoneNumber,fontSize=16.sp, color = Color.White)
            Text(text = item.packageName,fontSize=16.sp, color = Color.White)
            Text(text = item.price,fontSize=16.sp, color = Color.White)
            Text(text = SimpleDateFormat("hh:mm a , dd/MM/yy", Locale.ENGLISH).format(item.createdAt),
                fontSize=10.sp,color = Color.DarkGray)
        }
    }
}