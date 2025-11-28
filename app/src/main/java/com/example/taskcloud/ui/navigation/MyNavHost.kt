package com.example.taskcloud.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskcloud.ui.screens.taskeditor.TaskEditorScreen
import com.example.taskcloud.ui.screens.tasklist.TaskListScreen
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TaskListRoute
    ) {
        composable<TaskListRoute> {
            TaskListScreen(
                navigateToTaskEditor = { taskToEdit ->
                    if (taskToEdit != null) {
                        navController.navigate(TaskEditorRoute(taskToEdit.id))
                    } else {
                        navController.navigate(TaskEditorRoute())
                    }
                },
            )
        }

        composable<TaskEditorRoute> { entry ->
            TaskEditorScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

}

@Serializable
data object TaskListRoute

@Serializable
data class TaskEditorRoute(val taskToEditId: Long? = null)