package com.appdev.userlistdetails.data.remote.model

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: AddressDto,
    val company: CompanyDto
)
