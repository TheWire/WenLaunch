package com.thewire.wenlaunch.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.asUTC() : LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.of("Z"))
}
