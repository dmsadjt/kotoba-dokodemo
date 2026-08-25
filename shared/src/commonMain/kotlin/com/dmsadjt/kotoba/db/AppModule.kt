package com.dmsadjt.kotoba.db

import com.dmsadjt.kotoba.viewmodel.LookupViewModel
import com.dmsadjt.kotoba.MemoRepository
import com.dmsadjt.kotoba.viewmodel.MainViewModel
import com.dmsadjt.kotoba.viewmodel.MemoShareViewModel
import com.dmsadjt.kotoba.viewmodel.MemoViewModel
import com.dmsadjt.kotoba.viewmodel.ReviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createDatabase(get()) }
    single { get<KotobaDatabase>().memoQueries }
    single { MemoRepository(get()) }
    viewModel { MemoViewModel(get()) }
    single { LookupViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { MemoShareViewModel(get(), get(), get()) }
    viewModel { ReviewViewModel(get()) }
}