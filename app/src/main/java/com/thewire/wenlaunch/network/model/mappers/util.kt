package com.thewire.wenlaunch.network.model.mappers

import java.time.ZonedDateTime


fun getTimeObject(time: String?): ZonedDateTime? {
    val net = try {
        ZonedDateTime.parse(time)
    } catch (e: Exception) {
        null
    }
    return net
}