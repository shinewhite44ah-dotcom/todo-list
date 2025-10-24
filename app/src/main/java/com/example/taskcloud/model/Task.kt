package com.example.taskcloud.model

import com.example.taskcloud.database.entity.TaskEntity

data class Task(
    val id: Long? = null,
    val task: String,
    val isCompleted: Boolean = false,
)

fun Task.mapToEntity() = TaskEntity(
    id = id ?: 0,
    task = task,
    isCompleted = isCompleted
)
