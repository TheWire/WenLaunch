package com.thewire.wenlaunch.cache.model

interface IRepoToDomain<DomainModel> {
    fun mapToDomainModel(): DomainModel
}
