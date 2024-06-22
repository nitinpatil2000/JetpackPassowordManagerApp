package com.courses.passwordmanagerapp.home.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.courses.passwordmanagerapp.home.model.PasswordEntity

@Database(entities = [PasswordEntity::class], version = 1, exportSchema = false)
abstract class PasswordDatabase: RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}

