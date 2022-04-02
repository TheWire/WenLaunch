package com.thewire.wenlaunch.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

const val SECONDS_IN_DAY = 60L * 60L * 24L

fun ZonedDateTime.asUTC() : LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.of("Z"))
}

fun ZonedDateTime.toEpochMilliSecond() : Long {
    return this.toEpochSecond() * 1000L
}
