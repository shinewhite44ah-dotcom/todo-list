package com.example.taskcloud.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskcloud.database.dao.TaskDao
import com.example.taskcloud.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}