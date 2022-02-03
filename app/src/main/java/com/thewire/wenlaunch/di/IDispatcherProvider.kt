package com.thewire.wenlaunch.di

import kotlin.coroutines.CoroutineContext

interface IDispatcherProvider {
    fun getMainContext(): CoroutineContext
    fun getUIContext(): CoroutineContext
    fun getDefaultContext(): CoroutineContext
    fun getIOContext(): CoroutineContext
    fun getUnconfinedContext(): CoroutineContext
}