package com.dmsadjt.kotoba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.DictionaryEntry
import com.dmsadjt.kotoba.ocr.JapaneseNormalizer

class OcrLookupViewModel(
    private val dictionaryDataSource: DictionaryDataSource,
    private val japaneseNormalizer: JapaneseNormalizer
) {
    var searchResult by mutableStateOf<List<DictionaryEntry>>(emptyList())
        private set

    fun processOcrText(raw: String) {
        val normalizedResults = japaneseNormalizer.extractLookupCandidates(raw)
        searchResult = normalizedResults.mapNotNull { dictionaryDataSource.lookup(it) }
    }

    fun clearResults() {
        searchResult = emptyList()
    }
}