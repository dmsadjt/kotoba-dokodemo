package com.dmsadjt.kotoba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.MemoRepository
import com.dmsadjt.kotoba.srs.Grade
import com.dmsadjt.kotoba.srs.Sm2
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repository: MemoRepository
) : ViewModel() {

    private val _queue = mutableStateOf<List<Memo>>(emptyList())
    val queue : List<Memo> get() = _queue.value

    var currentIndex by mutableStateOf(0)
        private set

    var isFlipped by mutableStateOf(false)
        private set

    val currentMemo : Memo? get() = queue.getOrNull(currentIndex)
    val dueCount: Int get() = queue.size - currentIndex

    init {
        viewModelScope.launch {
            val due = repository.getDue(System.currentTimeMillis()).first()
            _queue.value = due
            currentIndex = 0
            isFlipped = false
        }
    }

    fun flip() {
        isFlipped = !isFlipped
    }

    fun grade(grade: Grade) {
        val memo = currentMemo ?: return
        val now = System.currentTimeMillis()
        val result = Sm2.nextReview(memo, grade, now)
        repository.updateReview(memo.id, result)
        currentIndex++
        isFlipped = false
    }

}