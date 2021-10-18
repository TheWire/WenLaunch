package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class RocketDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("configuration")
    var configuration: RocketConfigurationDto? = null
)
