package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName

data class LaunchDto(

    @SerializedName("id")
    var id: String? =  null,

    @SerializedName("url")
    var url: String? =  null,

    @SerializedName("name")
    var name: String? =  null,

    @SerializedName("net")
    var net: String? =  null,

    @SerializedName("rocket")
    var rocket: RocketDto? =  null,

    @SerializedName("mission")
    var mission: MissionDto? =  null,

    @SerializedName("pad")
    var pad: PadDto? =  null,

    @SerializedName("image")
    var image: String? =  null,



)
