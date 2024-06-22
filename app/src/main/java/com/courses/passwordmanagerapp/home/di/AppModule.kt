package com.courses.passwordmanagerapp.home.di

import android.content.Context
import androidx.room.Room
import com.courses.passwordmanagerapp.home.db.PasswordDao
import com.courses.passwordmanagerapp.home.db.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePasswordDao(passwordDatabase: PasswordDatabase): PasswordDao = passwordDatabase.passwordDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): PasswordDatabase =
        Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "passwordManagerDB"
        ).fallbackToDestructiveMigration().build()
}