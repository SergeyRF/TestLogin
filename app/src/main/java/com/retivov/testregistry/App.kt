package com.retivov.testregistry

import android.app.Application
import com.retivov.testregistry.di.myModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(myModel)
        }

        Timber.plant(Timber.DebugTree())
    }
}