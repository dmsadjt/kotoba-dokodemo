package com.dmsadjt.kotoba.db

import android.content.Context
import com.dmsadjt.kotoba.AndroidDictionaryDataSource
import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.clipboard.AndroidClipboardHandler
import com.dmsadjt.kotoba.clipboard.ClipboardHandler
import org.koin.dsl.module

fun androidModule(context: Context) = module {
    single { DatabaseDriverFactory(context) }
    single<DictionaryDataSource> { AndroidDictionaryDataSource(context) }
    single<ClipboardHandler> { AndroidClipboardHandler(context) }
}