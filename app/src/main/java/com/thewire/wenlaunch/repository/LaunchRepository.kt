package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.model.Launch

interface LaunchRepository {
    suspend fun upcoming(limit: Int) : List<Launch>
}