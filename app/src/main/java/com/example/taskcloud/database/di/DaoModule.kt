package com.example.taskcloud.database.di

import com.example.taskcloud.database.ToDoListDatabase
import com.example.taskcloud.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun providesTaskDao(
        database: ToDoListDatabase
    ): TaskDao {
        return database.taskDao()
    }
}