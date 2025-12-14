package com.appdev.userlistdetails.data.di

import android.content.Context
import com.appdev.userlistdetails.data.network.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkHandler(
        @ApplicationContext context: Context
    ): NetworkState = NetworkState(context)
}