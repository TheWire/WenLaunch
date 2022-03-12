package com.thewire.wenlaunch.network.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Location

data class LocationDto (
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("map_image")
    var map_image: String? = null,
) : IRepoToDomain<Location> {
    override fun mapToDomainModel(): Location {
        return Location(
            id = this.id,
            name = this.name ?: "",
            mapImage = this.map_image?.let { Uri.parse(this.map_image) }
        )
    }
}
