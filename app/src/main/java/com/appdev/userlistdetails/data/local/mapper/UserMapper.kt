package com.appdev.userlistdetails.data.local.mapper

import com.appdev.userlistdetails.data.local.entity.UserEntity
import com.appdev.userlistdetails.data.remote.model.UserDto
import com.appdev.userlistdetails.domain.model.User

fun UserDto.toEntity() =
    UserEntity(id, name, email, phone, website, address.city, company.name)

fun UserEntity.toDomainFromLocal() =
    User(id, name, email, phone, website, city, companyName)

fun UserDto.toDomainFromApi() =
    User(id, name, email, phone, website, address.city, company.name)


