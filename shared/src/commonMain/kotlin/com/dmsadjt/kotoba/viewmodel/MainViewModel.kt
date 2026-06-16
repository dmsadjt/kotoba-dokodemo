package com.dmsadjt.kotoba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    var sharedWord by mutableStateOf<String?>(null)

    fun updateSharedWord(word: String) {
        sharedWord = word
    }

    fun clearSharedWord() {
        sharedWord = null
    }
}