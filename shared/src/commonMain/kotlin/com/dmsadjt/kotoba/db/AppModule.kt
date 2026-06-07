package com.dmsadjt.kotoba.db

import com.dmsadjt.kotoba.LookupViewModel
import com.dmsadjt.kotoba.MemoRepository
import com.dmsadjt.kotoba.MemoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createDatabase(get()) }
    single { get<KotobaDatabase>().memoQueries }
    single { MemoRepository(get()) }
    viewModel { MemoViewModel(get()) }
    viewModel { LookupViewModel(get(), get()) }
}