package com.thewire.wenlaunch.network.responses

import com.thewire.wenlaunch.network.model.LaunchDto
import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var launches: List<LaunchDto>
)
