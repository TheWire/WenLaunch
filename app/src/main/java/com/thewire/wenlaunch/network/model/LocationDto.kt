package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class LocationDto (
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("map_image")
    var map_image: String? = null,
)
