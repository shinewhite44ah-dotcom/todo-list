package com.example.taskcloud.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import com.example.taskcloud.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    taskToEdit: Task?,
    onAddTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (taskToEdit != null) {
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
            taskToEdit = taskToEdit,
            onAddTask = { onAddTask(it) },
            onEditTask = { onEditTask(it) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AddTaskContent(
    taskToEdit: Task?,
    onAddTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var taskName by remember { mutableStateOf(taskToEdit?.task ?: "") }

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
            }
        ) {
            if (taskToEdit != null) {
                Text("Edit Task")
            } else {
                Text("Add new task")
            }
        }
    }
}

