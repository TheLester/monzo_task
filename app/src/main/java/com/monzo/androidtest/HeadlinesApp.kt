package com.monzo.androidtest

import android.app.Application
import com.monzo.androidtest.di.modules
import org.koin.core.context.startKoin

class HeadlinesApp : Application() {

    companion object {
        private lateinit var instance: HeadlinesApp
        fun get(): HeadlinesApp = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { modules(modules) }
    }

}
