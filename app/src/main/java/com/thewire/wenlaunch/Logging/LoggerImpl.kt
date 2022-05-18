package com.thewire.wenlaunch.Logging

import android.util.Log
import com.thewire.wenlaunch.Logging.model.LogEntry
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.repository.ILaunchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class LoggerImpl(
    val logToDatabase: Boolean = false,
    val repository: ILaunchRepository,
    val dispatcher: IDispatcherProvider
    ) : ILogger {
    override fun e(tag: String, message: String) {
        Log.e(tag, message)
        logToDatabase(message, "ERROR", tag)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
        logToDatabase(message, "WARNING", tag)

    }

    override fun v(tag: String, message: String) {
        Log.v(tag, message)
        logToDatabase(message, "VERBOSE", tag)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
        logToDatabase(message, "INFO", tag)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
        logToDatabase(message, "DEFAULT", tag)
    }

    private fun logToDatabase(message: String, level: String, tag: String) {
        if (this.logToDatabase) {
            CoroutineScope(dispatcher.getIOContext()).launch {
                repository.insertLog(
                    LogEntry(
                        message = message,
                        timestamp = System.currentTimeMillis(),
                        level = level,
                        tag = tag
                    )
                ).collect {}
            }
        }
    }
}