package com.appdev.userlistdetails.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String,
    val city: String,
    val companyName: String
)