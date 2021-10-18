package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class PadDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("location")
    var location: LocationDto? = null,
)
