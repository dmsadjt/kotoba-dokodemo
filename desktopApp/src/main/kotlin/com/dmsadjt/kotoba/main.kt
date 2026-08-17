package com.dmsadjt.kotoba

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dmsadjt.kotoba.db.appModule
import com.dmsadjt.kotoba.db.jvmModule
import com.dmsadjt.kotoba.viewmodel.OcrLookupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

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