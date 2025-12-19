package com.appdev.userlistdetails.data.di

import android.content.Context
import androidx.room.Room
import com.appdev.userlistdetails.data.local.dao.UserDao
import com.appdev.userlistdetails.data.local.database.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModule {
    @Provides
    @Singleton
    fun provideUserDataBase(@ApplicationContext context: Context): UserDataBase {
        return Room.databaseBuilder(context, UserDataBase::class.java, "user_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userPostDataBase: UserDataBase): UserDao {
        return userPostDataBase.userDao()
    }
}