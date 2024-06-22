package com.courses.passwordmanagerapp.home.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.courses.passwordmanagerapp.home.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password_manager")
    fun getAllPassword(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM password_manager WHERE password_id = :id")
    fun getPasswordFromId(id:Int): PasswordEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(password: PasswordEntity)

    @Delete
    suspend fun deletePassword(password: PasswordEntity)

    @Query("UPDATE password_manager SET accountName = :accountName, username = :username, password = :password WHERE password_id = :id")
    suspend fun updatePassword(accountName:String, password:String, username:String, id:Int)

}