package com.thewire.wenlaunch.network

import com.thewire.wenlaunch.network.model.LaunchDto
import com.thewire.wenlaunch.network.responses.LaunchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LaunchService {
    @GET("launch/upcoming")
    suspend fun upcoming(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : LaunchResponse

    @GET("launch/{id}")
    suspend fun launch(
        @Path("id") id: String
    ) : LaunchDto
}