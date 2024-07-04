package com.harsh.recyclerapplication

import android.icu.text.Transliterator.Position
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDao {
    @Insert
    fun insertToDo(taskDataClass: TaskDataClass)

    @Query("SELECT * FROM TaskDataClass")
    fun getList(): List<TaskDataClass>
    @Update
    fun updateTodo(taskDataClass: TaskDataClass)
    @Delete
    fun deleteToDo(taskDataClass: TaskDataClass)
}