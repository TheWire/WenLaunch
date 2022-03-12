package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Rocket

data class RocketDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("configuration")
    var configuration: RocketConfigurationDto? = null
) : IRepoToDomain<Rocket> {
    override fun mapToDomainModel(): Rocket {
        return Rocket(
            id = this.id,
            configuration = this.configuration?.mapToDomainModel(),
        )
    }
}
