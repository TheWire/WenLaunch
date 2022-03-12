package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.RocketConfiguration

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

) : IRepoToDomain<RocketConfiguration> {
    override fun mapToDomainModel(): RocketConfiguration {
        return RocketConfiguration(
            id = this.id,
            name = this.name ?: "",
            family = this.family ?: "",
            variant = this.variant ?: "",
            fullName = this.fullName ?: "",
        )
    }
}
