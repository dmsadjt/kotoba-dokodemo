package com.dmsadjt.kotoba

interface DictionaryDataSource {
    fun lookup(word: String) : DictionaryEntry?
}