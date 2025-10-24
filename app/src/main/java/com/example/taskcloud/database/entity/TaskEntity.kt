package com.example.taskcloud.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskcloud.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val task: String,
    val isCompleted: Boolean
)

fun TaskEntity.mapToTask() = Task(
    id = id,
    task = task,
    isCompleted = isCompleted,
)
