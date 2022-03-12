package com.thewire.wenlaunch.network.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.VidUri

data class VidURLDto(
    @SerializedName("url")
    var url: String? = null,
) : IRepoToDomain<VidUri> {
    override fun mapToDomainModel(): VidUri {
        return VidUri(
            uri = this.url?.let { Uri.parse(it) }
        )
    }
}