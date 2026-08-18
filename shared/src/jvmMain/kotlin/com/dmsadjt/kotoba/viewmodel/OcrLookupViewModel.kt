package com.dmsadjt.kotoba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.DictionaryEntry
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.MemoRepository
import com.dmsadjt.kotoba.ocr.JapaneseNormalizer

class OcrLookupViewModel(
    private val dictionaryDataSource: DictionaryDataSource,
    private val japaneseNormalizer: JapaneseNormalizer,
    private val memoRepository: MemoRepository
) {
    var searchResult by mutableStateOf<List<DictionaryEntry>>(emptyList())
        private set

    fun processOcrText(raw: String) {
        val normalizedResults = japaneseNormalizer.extractLookupCandidates(raw)
        searchResult = dictionaryDataSource.lookupBatch(normalizedResults)
    }

    fun saveMemo(entry: DictionaryEntry) {
        memoRepository.insert(
            Memo(
                word = entry.word,
                reading = entry.reading,
                meaning = entry.meaning
            )
        )
    }

    fun clearResults() {
        searchResult = emptyList()
    }
}