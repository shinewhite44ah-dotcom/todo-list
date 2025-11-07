package com.example.taskcloud.ui.screens.taskeditor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskcloud.data.TaskRepository
import com.example.taskcloud.data.TaskRepositoryImpl
import com.example.taskcloud.database.ToDoListDatabase
import com.example.taskcloud.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskEditorViewModel(
    private val taskToEditId: Long?,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val uiState: MutableStateFlow<TaskEditorUiState> = MutableStateFlow(TaskEditorUiState.Success())

    init {
        viewModelScope.launch {
            taskToEditId?.let {
                uiState.value = TaskEditorUiState.Loading
                // Simulate latency
                delay(1000)
                val taskToEdit = taskRepository.getTask(it)
                uiState.value = TaskEditorUiState.Success(taskToEdit)
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }
}

sealed interface TaskEditorUiState {
    data object Loading : TaskEditorUiState

    data class Success(val taskToEdit: Task? = null) : TaskEditorUiState
}

class TaskEditorViewModelFactory(
    private val context: Context,
    private val taskToEditId: Long?,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskEditorViewModel::class.java)) {
            val database = ToDoListDatabase.createDatabase(context)
            val taskDao = database.taskDao()
            val taskRepository = TaskRepositoryImpl(taskDao)
            return TaskEditorViewModel(taskToEditId, taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}