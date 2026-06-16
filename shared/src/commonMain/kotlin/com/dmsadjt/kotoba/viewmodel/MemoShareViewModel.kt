package com.dmsadjt.kotoba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmsadjt.kotoba.DictionaryDataSource
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.MemoRepository
import com.dmsadjt.kotoba.clipboard.ClipboardHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ShareResult {
    object Loading : ShareResult()
    data class Success(val memo: Memo, val isNew: Boolean): ShareResult()
    object NotFound : ShareResult()
}

class MemoShareViewModel(
    private val memoRepository: MemoRepository,
    private val dictionaryDataSource: DictionaryDataSource,
    private val clipboardHandler: ClipboardHandler
) : ViewModel() {
    private val _result = MutableStateFlow<ShareResult>(ShareResult.Loading)
    val result: StateFlow<ShareResult> = _result

    fun handleSharedWord(word: String) {
        viewModelScope.launch {
            val entry = dictionaryDataSource.lookup(word)
            if (entry == null) {
                _result.value = ShareResult.NotFound
                return@launch
            }
            val memo = Memo(word = entry.word, reading = entry.reading, meaning = entry.meaning)
            val isNew = !memoRepository.isExists(entry.word)
            if (isNew) {
                memoRepository.insert(memo)
            }
            val formatted = "${entry.word} • ${entry.reading}\n${entry.meaning}"
            clipboardHandler.copy(formatted)
            _result.value = ShareResult.Success(memo, isNew)
        }
    }
}