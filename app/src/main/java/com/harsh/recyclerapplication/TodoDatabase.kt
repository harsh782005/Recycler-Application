package com.harsh.recyclerapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskDataClass::class,ToDoEntity::class], version = 1, exportSchema = true)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao():ToDoDao
    companion object {
        private var todoDatabase: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            if (todoDatabase == null) {
                todoDatabase =
                    Room.databaseBuilder(
                        context, TodoDatabase::class.java,
                        "ToDoDatabase"
                    )
                        .allowMainThreadQueries()
                        .build()
            }

            return todoDatabase!!
        }
    }
}