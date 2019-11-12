package com.github.popular

import android.app.Application
import com.github.popular.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PopularApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@PopularApplication)
            modules(listOf(networkModule, appModule))
        }
    }
}