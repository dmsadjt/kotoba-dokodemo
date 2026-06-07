package com.dmsadjt.kotoba.db

import android.content.Context
import com.dmsadjt.kotoba.AndroidDictionaryDataSource
import com.dmsadjt.kotoba.DictionaryDataSource
import org.koin.dsl.module

fun androidModule(context: Context) = module {
    single { DatabaseDriverFactory(context) }
    single<DictionaryDataSource> { AndroidDictionaryDataSource(context) }
}