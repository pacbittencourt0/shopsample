package com.pacbittencourt.shop

import android.app.Application
import com.pacbittencourt.shop.di.useCaseModule
import com.pacbittencourt.shop.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ShopApplication)
            modules(viewModelModule, useCaseModule)
        }
    }
}