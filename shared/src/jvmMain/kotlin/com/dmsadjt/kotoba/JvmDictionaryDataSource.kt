package com.dmsadjt.kotoba

import java.sql.DriverManager

class JvmDictionaryDataSource : DictionaryDataSource {
    private val dbPath : String by lazy {
        println("dbPath lazy block running")
        val appDir = java.io.File(System.getProperty("user.home"), ".kotoba")
        appDir.mkdirs()
        val dbFile = java.io.File(appDir, "jmdict.db")
        if (!dbFile.exists()) {
            val resource = (Thread.currentThread().contextClassLoader
                ?: JvmDictionaryDataSource::class.java.classLoader)
                .getResourceAsStream("files/jmdict.db")
            println("Resource found: ${resource != null}")
            resource?.use { input ->
                dbFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        dbFile.absolutePath
    }

    override fun lookup(word: String): DictionaryEntry? {
        val conn = DriverManager.getConnection("jdbc:sqlite:$dbPath")
        val stmt = conn.prepareStatement("SELECT id, word, reading, meaning FROM dictionary WHERE word = ? LIMIT 1")
        stmt.setString(1, word)
        val rs = stmt.executeQuery()
        return if (rs.next()) {
            DictionaryEntry(
                id = rs.getLong("id"),
                word = rs.getString("word"),
                reading = rs.getString("reading"),
                meaning = rs.getString("meaning")
            )
        } else null
    }
}