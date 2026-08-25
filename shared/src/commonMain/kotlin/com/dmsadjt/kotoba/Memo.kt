package com.dmsadjt.kotoba

data class Memo(
    val id: Long = 0,
    val word: String,
    val reading: String,
    val meaning: String,
    val savedAt: Long = System.currentTimeMillis(),
    val easeFactor: Double = 2.5,
    val intervalDays: Long = 0,
    val repetitions: Long = 0,
    val dueAt: Long = System.currentTimeMillis(),
    val lastReviewedAt: Long? = null
)