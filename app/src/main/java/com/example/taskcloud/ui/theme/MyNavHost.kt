package com.example.taskcloud.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.taskcloud.TaskEditorScreen
import com.example.taskcloud.TaskListScreen
import com.example.taskcloud.mapToEntity
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val database = remember { ToDoListDatabase.createDatabase(context.applicationContext) }
    val taskDao = remember { database.taskDao() }

    val tasks by taskDao.getTasks().collectAsState(emptyList())

    NavHost(
        navController = navController,
        startDestination = TaskListRoute
    ) {
        composable<TaskListRoute> {
            TaskListScreen(
                taskList = tasks.map { it.mapToTask() },
                navigateToAddTask = {
                    navController.navigate(TaskEditorRoute())
                },
                onIsCompletedChange = { task ->
                    taskDao.update(task.mapToEntity())
                },
                onDelete = { task ->
                    taskDao.delete(task.mapToEntity())
                },
                onEdit = { task ->
                    navController.navigate(TaskEditorRoute(task.id))
                }
            )
        }

        composable<TaskEditorRoute> { entry ->
            val taskToEditId = entry.toRoute<TaskEditorRoute>().taskToEditId

            val taskToEdit = if (taskToEditId != null) {
                taskDao.getTask(taskToEditId)?.mapToTask()
            } else {
                null
            }

            TaskEditorScreen(
                taskToEdit = taskToEdit,
                onAddTask = { task ->
                    taskDao.insert(task.mapToEntity())
                    navController.popBackStack()
                },
                onEditTask = { task ->
                    taskDao.update(task.mapToEntity())
                    navController.popBackStack()
                },
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