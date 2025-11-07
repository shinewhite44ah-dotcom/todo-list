package com.example.taskcloud.ui.screens.tasklist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskcloud.data.TaskRepository
import com.example.taskcloud.data.TaskRepositoryImpl
import com.example.taskcloud.database.ToDoListDatabase
import com.example.taskcloud.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    val tasks: StateFlow<List<Task>> =
        taskRepository.getTasks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}

class TaskListViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            val database = ToDoListDatabase.createDatabase(context)
            val taskDao = database.taskDao()
            val taskRepository = TaskRepositoryImpl(taskDao)
            return TaskListViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}