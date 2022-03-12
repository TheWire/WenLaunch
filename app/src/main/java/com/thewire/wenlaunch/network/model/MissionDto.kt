package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Mission

data class MissionDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("orbit")
    var orbit: OrbitDto? = null,
) :IRepoToDomain<Mission> {
    override fun mapToDomainModel(): Mission {
        return Mission(
            id = this.id,
            name = this.name ?: "",
            description = this.description ?: "",
            orbit = this.orbit?.mapToDomainModel(),
        )
    }
}
