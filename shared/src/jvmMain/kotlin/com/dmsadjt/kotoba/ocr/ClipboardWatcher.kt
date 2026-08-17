package com.dmsadjt.kotoba.ocr

import com.dmsadjt.kotoba.viewmodel.OcrLookupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class ClipboardWatcher(
    private val ocrLookupViewModel: OcrLookupViewModel
) {
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var job: Job? = null
    val japaneseTextRegex = Regex("[\u3040-\u309F\u30A0-\u30FF\u4E00-\u9FFF]")

    fun start() {
        job = appScope.launch {
            var clipboardValue = ""
            while (isActive) {
                try {
                    val currentClipboardValue = Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as? String ?: ""
                    if (clipboardValue != currentClipboardValue) {
                        clipboardValue = currentClipboardValue
                        if (japaneseTextRegex.containsMatchIn(clipboardValue)) {
                            ocrLookupViewModel.processOcrText(clipboardValue)
                            println("OCR results: ${ocrLookupViewModel.searchResult}")
                        }
                    }
                } catch (e: Exception) {

                }
                delay(400)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}
