package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class RocketConfigurationDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("family")
    var family: String? = null,

    @SerializedName("full_name")
    var fullName: String? = null,

    @SerializedName("variant")
    var variant: String? = null,

)
