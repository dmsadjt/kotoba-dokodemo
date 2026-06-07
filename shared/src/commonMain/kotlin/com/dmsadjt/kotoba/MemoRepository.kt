package com.dmsadjt.kotoba

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.dmsadjt.kotoba.db.MemoQueries
import com.dmsadjt.kotoba.db.Memos
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

    fun insert(memo:Memo) = queries.insert(memo.word, memo.reading, memo.meaning, memo.savedAt)

    fun deleteById(id: Long) = queries.deleteById(id)
}


private fun com.dmsadjt.kotoba.db.Memos.toMemo() = Memo(
    id = id,
    word = word,
    reading = reading,
    meaning = meaning,
    savedAt = savedAt
)