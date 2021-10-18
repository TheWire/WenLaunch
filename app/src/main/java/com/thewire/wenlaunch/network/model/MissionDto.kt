package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class MissionDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("orbit")
    var orbit: OrbitDto? = null,
)
