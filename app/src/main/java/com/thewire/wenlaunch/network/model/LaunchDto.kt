package com.thewire.wenlaunch.network.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.network.model.mappers.getTimeObject

data class LaunchDto(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("status")
    var status: StatusDto? = null,

    @SerializedName("net")
    var net: String? = null,

    @SerializedName("rocket")
    var rocket: RocketDto? = null,

    @SerializedName("mission")
    var mission: MissionDto? = null,

    @SerializedName("pad")
    var pad: PadDto? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("webcast_live")
    var webcastLive: Boolean? = null,

    @SerializedName("vidURLs")
    var vidURLs: List<VidURLDto>? = null,
) : IRepoToDomain<Launch> {
    override fun mapToDomainModel(): Launch {
        return Launch(
            id = this.id ?: "",
            url = this.url?.let { url -> Uri.parse(url) },
            name = this.name ?: "",
            status = this.status?.mapToDomainModel(),
            net = { getTimeObject(this.net) },
            rocket = this.rocket?.mapToDomainModel(),
            mission = this.mission?.mapToDomainModel(),
            pad = this.pad?.mapToDomainModel(),
            image = this.image?.let { Uri.parse(this.image) },
            webcastLive = this.webcastLive ?: false,
            vidUris = this.vidURLs?.map { it.mapToDomainModel() } ?: listOf()
        )
    }
}
