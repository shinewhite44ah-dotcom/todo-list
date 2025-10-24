package com.example.taskcloud.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskcloud.AddTaskScreen
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
    ){
        composable<TaskListRoute> {
            TaskListScreen(
                taskList = tasks.map { it.mapToTask() },
                navigateToAddTask = {
                    navController.navigate(AddTaskRoute)
                },
                onIsCompletedChange = { task ->
                    taskDao.update(task.mapToEntity())
                }
            )
        }
        composable<AddTaskRoute>{
            AddTaskScreen(
                onAddTask = { task ->
                    taskDao.insert(task.mapToEntity())
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
data object AddTaskRoute