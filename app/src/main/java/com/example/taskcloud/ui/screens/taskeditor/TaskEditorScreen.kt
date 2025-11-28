package com.example.taskcloud.ui.screens.taskeditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskcloud.model.Task

@Composable
fun TaskEditorScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: TaskEditorViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskEditorScreen(
        uiState = uiState,
        onAddTask = {
            viewModel.addTask(it)
            onBackClick()
        },
        onUpdateTask = {
            viewModel.updateTask(it)
            onBackClick()
        },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    uiState: TaskEditorUiState,
    onAddTask: (Task) -> Unit,
    onUpdateTask: (Task) -> Unit,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        TaskEditorUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is TaskEditorUiState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            if (uiState.taskToEdit != null) {
                                Text("Edit Task")
                            } else {
                                Text("Add Task")
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { onBackClick() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                AddTaskContent(
                    taskToEdit = uiState.taskToEdit,
                    onAddTask = { onAddTask(it) },
                    onEditTask = { onUpdateTask(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun AddTaskContent(
    taskToEdit: Task?,
    onAddTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var taskName by remember(taskToEdit) { mutableStateOf(taskToEdit?.task ?: "") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            placeholder = {
                Text(text = "Enter your task")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (taskToEdit != null) {
                    onEditTask(taskToEdit.copy(task = taskName))
                } else {
                    onAddTask(Task(task = taskName))
                }
            },
            enabled = taskName.isNotBlank()
        ) {
            if (taskToEdit != null) {
                Text("Edit Task")
            } else {
                Text("Add new task")
            }
        }
    }
}

