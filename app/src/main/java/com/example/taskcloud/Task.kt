package com.example.taskcloud

import com.example.taskcloud.ui.theme.TaskEntity

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
