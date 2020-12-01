package com.example.msg.di.app

import android.app.Application
import com.example.msg.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        const val BASE_URL = ""
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                activityModule,
                fragmentModule,
                networkModule,
                apiModule
            )
        }
    }
}