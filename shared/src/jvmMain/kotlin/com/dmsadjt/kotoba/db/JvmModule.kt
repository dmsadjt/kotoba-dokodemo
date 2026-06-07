package com.dmsadjt.kotoba.db

import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.JvmDictionaryDataSource
import org.koin.dsl.module


val jvmModule = module {
    single { DatabaseDriverFactory() }
    single<DictionaryDataSource> { JvmDictionaryDataSource() }
}