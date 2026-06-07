package com.dmsadjt.kotoba

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LookupViewModel(
    private val memoRepository: MemoRepository,
    private val dictionaryDataSource: DictionaryDataSource
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var searchResult by mutableStateOf<DictionaryEntry?>(null)
        private set

    fun onQueryChange(query: String) {
        searchQuery = query
    }

    fun lookupWord(query: String) {
        searchResult = dictionaryDataSource.lookup(query)
    }

    fun saveMemo(entry: DictionaryEntry) {
        memoRepository.insert(Memo(
            word = entry.word,
            reading = entry.reading,
            meaning = entry.meaning
        ))
    }
}