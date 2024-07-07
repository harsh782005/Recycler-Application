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
    @Query("SELECT * FROM TaskDataClass WHERE tvPriority = :tvPriority")
    fun TaskAccPriorit(tvPriority : Int): List<TaskDataClass>
    @Insert
    fun insertTodoItem(toDoEntity: ToDoEntity)
    @Query("SELECT * FROM todoentity WHERE taskId=:taskId")
    fun getToDoList(taskId:Int):List<ToDoEntity>
    @Update
    fun updateToDoItem(toDoEntity: ToDoEntity)
    @Delete
    fun deleteToDoItem(toDoEntity: ToDoEntity)
}