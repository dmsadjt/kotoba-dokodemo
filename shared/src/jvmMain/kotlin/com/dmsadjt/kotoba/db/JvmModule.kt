package com.dmsadjt.kotoba.db

import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.JvmDictionaryDataSource
import com.dmsadjt.kotoba.ocr.ClipboardWatcher
import com.dmsadjt.kotoba.ocr.JapaneseNormalizer
import com.dmsadjt.kotoba.viewmodel.OcrLookupViewModel
import org.koin.dsl.module


val jvmModule = module {
    single { DatabaseDriverFactory() }
    single<DictionaryDataSource> { JvmDictionaryDataSource() }
    single { JapaneseNormalizer() }
    single { OcrLookupViewModel(get(), get(), get())  }
    single { ClipboardWatcher(get()) }
}