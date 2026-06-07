package com.dmsadjt.kotoba

data class Memo(
    val id: Long = 0,
    val word: String,
    val reading: String,
    val meaning: String,
    val savedAt: Long = System.currentTimeMillis()
)