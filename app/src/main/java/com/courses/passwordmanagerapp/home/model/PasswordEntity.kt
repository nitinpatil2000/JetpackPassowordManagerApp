package com.courses.passwordmanagerapp.home.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("password_manager")
data class PasswordEntity(
    @ColumnInfo("accountName")
    val accountName: String,

    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("password")
    val password: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("password_id")
    val id: Int = 0,

    )