package com.thewire.wenlaunch.network

import com.thewire.wenlaunch.network.responses.LaunchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LaunchService {
    @GET("upcoming")
    suspend fun upcoming(
        @Query("limit") limit: Int
    ) : LaunchResponse
}