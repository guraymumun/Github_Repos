package com.example.githubrepos

import android.app.Application
import com.example.githubrepos.di.presentationModule
import com.example.githubrepos.di.remoteModule
import com.example.githubrepos.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(repositoryModule, remoteModule, presentationModule)
        }
    }
}