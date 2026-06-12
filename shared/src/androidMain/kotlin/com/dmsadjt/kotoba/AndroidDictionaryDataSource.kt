package com.dmsadjt.kotoba

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AndroidDictionaryDataSource(context: Context) : DictionaryDataSource {
    private val db: SQLiteDatabase by lazy {
        val dbFile = context.getDatabasePath("jmdict.db")
        if (!dbFile.exists()) {
            dbFile.parentFile?.mkdirs()
            context.assets.open("jmdict.db").use { input ->
                dbFile.outputStream().use { outputStream ->
                    input.copyTo(outputStream)
                }
            }
        }
        SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
    }

    override fun lookup(word: String) : DictionaryEntry? {
        val cursor = db.rawQuery(
            "SELECT id, word, reading, meaning FROM dictionary WHERE word = ? LIMIT 1",
            arrayOf(word)
        )
        return cursor.use {
            if (it.moveToFirst()) {
                DictionaryEntry(
                    id = it.getLong(0),
                    word = it.getString(1),
                    reading = it.getString(2),
                    meaning = it.getString(3)
                )
            } else null
        }
    }
}