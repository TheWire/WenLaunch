package com.thewire.wenlaunch.logging

import com.thewire.wenlaunch.Logging.ILogger

class MockLogger: ILogger {
    override fun e(tag: String, message: String) {
        println("$tag: Error: $message")
    }

    override fun w(tag: String, message: String) {
        println("$tag: Warning: $message")
    }

    override fun v(tag: String, message: String) {
        println("$tag: Verbose: $message")
    }

    override fun i(tag: String, message: String) {
        println("$tag: Information: $message")
    }

    override fun d(tag: String, message: String) {
        println("$tag: Debug: $message")
    }
}