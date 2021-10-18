package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class RocketConfigurationDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

)
