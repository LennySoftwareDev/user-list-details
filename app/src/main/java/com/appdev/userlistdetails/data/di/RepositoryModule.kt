package com.appdev.userlistdetails.data.di

import com.appdev.userlistdetails.data.repository.UserRepositoryImpl
import com.appdev.userlistdetails.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        implement: UserRepositoryImpl
    ): UserRepository
}