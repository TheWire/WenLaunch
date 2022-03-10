package com.thewire.wenlaunch.util

fun String.ifEmptyNull(): String? {
    if(this.isEmpty()) {
        return null
    }
    return this
}