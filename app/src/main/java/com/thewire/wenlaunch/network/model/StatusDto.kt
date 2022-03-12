package com.thewire.wenlaunch.network.model

import com.google.gson.annotations.SerializedName
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.Status

data class StatusDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("abbrev")
    var abbrev: String? = null,

    @SerializedName("description")
    var description: String? = null,
) : IRepoToDomain<Status> {
    override fun mapToDomainModel(): Status {
        return Status(
            id = this.id,
            name = this.name ?: "",
            abbrev = LaunchStatus.getLaunchStatus(this.abbrev),
            description = this.description ?: "",
        )
    }
}