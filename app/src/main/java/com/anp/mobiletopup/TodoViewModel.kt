package com.anp.mobiletopup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anp.mobiletopup.db.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {
    val todoDao = MainApplication.todoDatabase.getTodoDao()
    var todoList : LiveData<List<Todo>> = todoDao.getAllTodo()
    var remainingBalance = mutableStateOf(15000)
    var rechargeNumber= mutableStateOf("")
    var rechargeOperator= mutableStateOf("")

    @RequiresApi(Build.VERSION_CODES.O)

    fun addTodo(packageName: String, operatorName: String, price: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(
                    packageName = packageName,
                    operatorName = operatorName,
                    price = price,
                    phoneNumber = phoneNumber,
                    createdAt = Date.from(Instant.now())
                )
            )
        }
    }

    fun deleteTodo(id : Int){
        viewModelScope.launch (Dispatchers.IO){
            todoDao.deleteTodo(id)
        }
    }

    fun getTodoById(id: Int): LiveData<Todo> {
        return todoDao.getTodoById(id)
    }



}