package com.thewire.wenlaunch.cache.model.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.RocketConfiguration

@Entity(tableName = "rocket_configuration")
data class RocketConfigurationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "family")
    val family: String,
    @ColumnInfo(name = "variant")
    val variant: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
) : IRepoToDomain<RocketConfiguration> {
    override fun mapToDomainModel(): RocketConfiguration {
        return RocketConfiguration(
            id = this.id,
            name = this.name,
            family = this.family,
            variant = this.variant,
            fullName = this.fullName,
        )
    }
}

fun RocketConfiguration.mapToEntity(): RocketConfigurationEntity {
    return RocketConfigurationEntity(
        id = this.id ?: throw(IllegalArgumentException("primary key null")),
        name = this.name,
        family = this.family,
        variant = this.variant,
        fullName = this.fullName,
        modifiedAt = System.currentTimeMillis()
    )
}
