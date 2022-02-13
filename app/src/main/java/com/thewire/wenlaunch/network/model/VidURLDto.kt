package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class VidURLDto(
    @SerializedName("url")
    var url: String? = null,
)