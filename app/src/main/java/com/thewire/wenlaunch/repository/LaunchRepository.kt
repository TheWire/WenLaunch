package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.model.Launch

interface LaunchRepository {
    suspend fun upcoming(limit: Int, offset: Int) : List<Launch>
    suspend fun launch(id: String) : Launch
}