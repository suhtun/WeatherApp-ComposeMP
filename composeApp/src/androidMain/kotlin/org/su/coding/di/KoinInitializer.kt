package org.su.coding.di

import android.content.Context
import appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context
) {

    actual fun init(){
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }
}