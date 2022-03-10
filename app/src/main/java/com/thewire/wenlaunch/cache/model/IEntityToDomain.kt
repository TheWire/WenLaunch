package com.thewire.wenlaunch.cache.model

interface IEntityToDomain<DomainModel> {
    fun mapToDomain(): DomainModel
}