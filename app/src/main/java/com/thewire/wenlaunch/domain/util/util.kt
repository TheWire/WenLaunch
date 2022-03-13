package com.thewire.wenlaunch.network.model.mappers

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime


fun getTimeObject(time: String?): ZonedDateTime {
    val net = try {
        ZonedDateTime.parse(time)
    } catch (e: Exception) {
        ZonedDateTime.ofInstant(Instant.MIN, ZoneOffset.UTC)
    }
    return net
}