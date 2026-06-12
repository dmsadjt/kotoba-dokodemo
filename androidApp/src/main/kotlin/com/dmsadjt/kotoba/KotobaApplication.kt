package com.dmsadjt.kotoba

import android.app.Application
import com.dmsadjt.kotoba.db.androidModule
import com.dmsadjt.kotoba.db.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotobaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotobaApplication)
            modules(androidModule(this@KotobaApplication), appModule)
        }
    }
}