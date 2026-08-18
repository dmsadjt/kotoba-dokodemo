package com.dmsadjt.kotoba

interface DictionaryDataSource {
    fun lookup(word: String) : DictionaryEntry?
    fun lookupBatch(wordList: List<String>) : List<DictionaryEntry>
}