package com.anp.mobiletopup.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anp.mobiletopup.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM TODO ORDER BY createdAt DESC")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo:Todo)

    @Query("Delete FROM Todo Where id = :id")
    fun deleteTodo(id:Int)

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getTodoById(id: Int): LiveData<Todo>

}