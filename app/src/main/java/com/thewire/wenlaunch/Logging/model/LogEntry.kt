package com.thewire.wenlaunch.Logging.model

data class LogEntry(
    val logId : Int = 0,
    val message: String,
    val timestamp: Long,
    val level: String,
    val tag: String,
)
