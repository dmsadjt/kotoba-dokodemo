package com.dmsadjt.kotoba.srs

import com.dmsadjt.kotoba.Memo

enum class Grade(val quality: Int) {
    AGAIN(2),
    HARD(3),
    GOOD(4),
    EASY(5)
}

data class ReviewResult(
    val easeFactor: Double,
    val intervalDays: Long,
    val repetitions: Long,
    val dueAt: Long,
    val lastReviewedAt: Long
)

private const val MIN_EASE = 1.3
private const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L

object Sm2 {
    fun nextReview(current: Memo, grade: Grade, now: Long): ReviewResult {

        val newEase = adjustedEase(current.easeFactor, grade)

        val (newRepetitions, newInterval) = if (grade == Grade.AGAIN) {
            0L to 1L
        } else {
            val repetitions = current.repetitions + 1
            val interval = when (repetitions) {
                1L -> 1L
                2L -> 6L
                else -> (current.intervalDays * newEase).toLong().coerceAtLeast(1L)
            }
            repetitions to interval
        }

        return ReviewResult(
            easeFactor = newEase,
            intervalDays = newInterval,
            repetitions = newRepetitions,
            dueAt = now + newInterval * MILLIS_PER_DAY,
            lastReviewedAt = now
        )
    }

    private fun adjustedEase(currentEase: Double, grade: Grade): Double {
        val q = grade.quality
        val delta = 0.1 - (5 - q) * (0.08 + (5 - q) * 0.02)
        return (currentEase + delta).coerceAtLeast(MIN_EASE)
    }
}