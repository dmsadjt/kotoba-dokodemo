package com.dmsadjt.kotoba

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dmsadjt.kotoba.db.appModule
import com.dmsadjt.kotoba.db.jvmModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(jvmModule, appModule)
    }

    application {

        Window(
            onCloseRequest = ::exitApplication,
            title = "Kotoba",
        ) {
            App()
        }
    }
}