package com.example.taskcloud.database

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromEpochDays(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun toEpochDays(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}
