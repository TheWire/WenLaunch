package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Pad

data class PadDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("location")
    var location: LocationDto? = null,
) : IRepoToDomain<Pad> {
    override fun mapToDomainModel(): Pad {
        return Pad(
            id = this.id,
            name = this.name ?: "",
            location = this.location?.mapToDomainModel(),
        )
    }
}
