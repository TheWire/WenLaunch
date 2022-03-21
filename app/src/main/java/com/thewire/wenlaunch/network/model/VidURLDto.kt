package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.VidUrl

data class VidURLDto(
    @SerializedName("url")
    var url: String? = null,
) : IRepoToDomain<VidUrl> {
    override fun mapToDomainModel(): VidUrl {
        return VidUrl(
            uri = this.url ?: ""
        )
    }
}