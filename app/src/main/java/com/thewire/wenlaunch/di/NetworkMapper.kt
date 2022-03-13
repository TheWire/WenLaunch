package com.thewire.wenlaunch.di

import com.google.gson.GsonBuilder
import com.thewire.wenlaunch.BuildConfig
import com.thewire.wenlaunch.network.LaunchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkMapper {

    @Singleton
    @Provides
    fun provideLaunchService(): LaunchService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(LaunchService::class.java)
    }
}