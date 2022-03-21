package com.thewire.wenlaunch.di

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object TestDispatcherProvider : IDispatcherProvider {

    override fun getMainContext(): CoroutineContext {
        return Dispatchers.Default
    }

    override fun getUIContext(): CoroutineContext {
        return Dispatchers.Default
    }

    override fun getDefaultContext(): CoroutineContext {
        return Dispatchers.Default
    }

    override fun getIOContext(): CoroutineContext {
        return Dispatchers.IO
    }

    override fun getUnconfinedContext(): CoroutineContext {
        return Dispatchers.Unconfined
    }
}