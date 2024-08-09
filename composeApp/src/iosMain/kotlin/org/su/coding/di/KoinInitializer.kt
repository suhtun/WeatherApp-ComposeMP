package org.su.coding.di

import appModule
import org.koin.core.context.startKoin

actual class KoinInitializer {

    actual fun init(){
        startKoin {
            modules(appModule, viewModelModule)
        }
    }
}