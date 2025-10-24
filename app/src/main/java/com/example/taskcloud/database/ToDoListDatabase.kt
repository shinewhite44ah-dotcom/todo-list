package com.example.taskcloud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskcloud.database.dao.TaskDao
import com.example.taskcloud.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract fun  taskDao(): TaskDao
    companion object {
        fun createDatabase(context: Context): ToDoListDatabase {
            return Room.databaseBuilder(
                context,
                ToDoListDatabase::class.java,
                "TodoList-db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}