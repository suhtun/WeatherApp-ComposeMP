package org.su.coding

import org.su.coding.di.KoinInitializer
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController (
    configure = {
        KoinInitializer().init()
    }
){ App() }