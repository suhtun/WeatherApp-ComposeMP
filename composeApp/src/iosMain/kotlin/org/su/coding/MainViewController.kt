package org.su.coding

import KoinInitializer
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController (
    configure = {
        KoinInitializer().init()
    }
){ App() }