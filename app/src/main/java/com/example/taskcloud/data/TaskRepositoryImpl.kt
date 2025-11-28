package com.example.taskcloud.data

import com.example.taskcloud.database.dao.TaskDao
import com.example.taskcloud.database.entity.mapToTask
import com.example.taskcloud.model.Task
import com.example.taskcloud.model.mapToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { taskEntities -> taskEntities.map { it.mapToTask() } }
    }

    override suspend fun getTask(taskId: Long): Task? {
        return taskDao.getTask(taskId)?.mapToTask()
    }

    override suspend fun addTask(task: Task) {
        taskDao.insert(task.mapToEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.mapToEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.mapToEntity())
    }
}