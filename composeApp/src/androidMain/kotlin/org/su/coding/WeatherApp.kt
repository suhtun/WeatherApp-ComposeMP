package org.su.coding

import org.su.coding.di.KoinInitializer
import android.app.Application

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInitializer(applicationContext).init()
    }
}