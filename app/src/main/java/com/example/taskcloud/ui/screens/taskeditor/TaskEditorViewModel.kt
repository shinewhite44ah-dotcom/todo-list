package com.example.taskcloud.ui.screens.taskeditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.taskcloud.data.TaskRepository
import com.example.taskcloud.model.Task
import com.example.taskcloud.ui.navigation.TaskEditorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val taskToEditId: Long? = savedStateHandle.toRoute<TaskEditorRoute>().taskToEditId

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
