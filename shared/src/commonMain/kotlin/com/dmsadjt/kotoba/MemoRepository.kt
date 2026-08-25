package com.dmsadjt.kotoba

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dmsadjt.kotoba.db.MemoQueries
import com.dmsadjt.kotoba.db.Memos
import com.dmsadjt.kotoba.srs.ReviewResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemoRepository(private val queries: MemoQueries) {
    fun getAll(): Flow<List<Memo>> = queries.getAll()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map { list -> list.map { it.toMemo() } }

    fun search(query: String): Flow<List<Memo>> {
        val q = "%$query"
        return queries
            .search(q,q,q).asFlow().mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toMemo() } }
    }

    fun insert(memo:Memo) = queries.insert(memo.word, memo.reading, memo.meaning, memo.savedAt, memo.dueAt)

    fun isExists(word: String): Boolean = queries.isExists(word).executeAsOne()

    fun deleteById(id: Long) = queries.deleteById(id)

    fun getDue(now: Long) : Flow<List<Memo>> = queries.getDue(now)
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map {list -> list.map { it.toMemo() }}

    fun countDue(now: Long) : Long = queries.countDue(now).executeAsOne()

    fun updateReview(id: Long, result: ReviewResult) = queries.updateReview(
        result.easeFactor,
        result.intervalDays,
        result.repetitions,
        result.dueAt,
        result.lastReviewedAt,
        id
    )
}


private fun com.dmsadjt.kotoba.db.Memos.toMemo() = Memo(
    id = id,
    word = word,
    reading = reading,
    meaning = meaning,
    savedAt = savedAt,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    dueAt = dueAt,
    lastReviewedAt = lastReviewedAt
)