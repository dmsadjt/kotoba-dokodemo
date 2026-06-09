package com.dmsadjt.kotoba.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val appDir = java.io.File(System.getProperty("user.home"), ".kotoba")
        appDir.mkdirs()
        val dbFile = java.io.File(appDir, "kotoba.db")
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")
        if (!dbFile.exists()) {
            KotobaDatabase.Schema.create(driver)
        }
        return driver
    }
}