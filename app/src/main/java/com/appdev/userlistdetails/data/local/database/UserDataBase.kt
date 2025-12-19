package com.appdev.userlistdetails.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appdev.userlistdetails.data.local.dao.UserDao
import com.appdev.userlistdetails.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}