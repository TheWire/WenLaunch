package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class OrbitDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("abbrev")
    var abbrev: String? = null,
)
