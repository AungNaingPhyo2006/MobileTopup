package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopupHistory(navController: NavController , modifier: Modifier = Modifier, viewModel : TodoViewModel) {
    val historyList by viewModel.todoList.observeAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = historyList?.filter { item ->
        item.operatorName.contains(searchQuery, ignoreCase = true)
    }

    Column (modifier = Modifier){
        TopAppBar(
            title = { Text("History") },
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
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search transactions") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Column (modifier = modifier.fillMaxWidth()) {
            filteredList?.let {
                LazyColumn(
                    content = {
                        itemsIndexed(it){ _:Int, item : Todo ->
                            TodoItem(item = item ,onDelete = {viewModel.deleteTodo(item.id)},
                                onClick = { id -> navController.navigate(Routes.detailPage + "/${id}") })
                        }
                    }
                )
            }?: Text(modifier = Modifier.fillMaxWidth().padding(8.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,text = "No Items yet!")

        }
    }

}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(item.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = SimpleDateFormat("hh:mm a, dd/MM/yy", Locale.ENGLISH).format(item.createdAt),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${item.operatorName} သို့ ပေးချေခြင်း",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

//@Composable
//fun TodoItem(item :Todo , onDelete : ()->Unit , onClick: (Int) -> Unit) {
//    Row(modifier = Modifier.fillMaxWidth()
//        .padding(8.dp)
//        .clip(RoundedCornerShape(16.dp))
//        .background(Color.Magenta)
//        .clickable{ onClick(item.id) }
//        .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = SimpleDateFormat("hh:mm a , dd/MM/yy", Locale.ENGLISH).format(item.createdAt)
//                ,fontSize=10.sp,color = Color.White
//            )
//            Text(text = "${item.operatorName} သို့ ပေးချေခြင်း",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium,
//                color = MaterialTheme.colorScheme.onSurfaceVariant)
//        }
//        IconButton(onClick = onDelete) {
//            Icon(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "Delete",
//                tint = Color.White)
//        }
//    }
//}