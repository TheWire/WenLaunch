package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Orbit

data class OrbitDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("abbrev")
    var abbrev: String? = null,
) : IRepoToDomain<Orbit> {
    override fun mapToDomainModel(): Orbit {
        return Orbit(
            id = this.id,
            name = this.name ?: "",
            abbrev = this.abbrev ?: ""
        )
    }
}
