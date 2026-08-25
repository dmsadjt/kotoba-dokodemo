package com.dmsadjt.kotoba.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val appDir = java.io.File(System.getProperty("user.home"), ".kotoba")
        appDir.mkdirs()
        val dbFile = java.io.File(appDir, "kotoba.db")
        val alreadyExisted = dbFile.exists()
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")

        if (!alreadyExisted) {
            KotobaDatabase.Schema.create(driver)
            driver.setUserVersion(KotobaDatabase.Schema.version)
        } else {
            val currentVersion = driver.getUserVersion()
            val targetVersion = KotobaDatabase.Schema.version
            if (currentVersion < targetVersion) {
                KotobaDatabase.Schema.migrate(driver, currentVersion, targetVersion)
                driver.setUserVersion(targetVersion)
            }
        }

        return driver
    }
}

private fun SqlDriver.getUserVersion(): Long =
    executeQuery(
        identifier = null,
        sql = "PRAGMA user_version",
        mapper = { cursor -> QueryResult.Value(if (cursor.next().value) cursor.getLong(0) ?: 0L else 0L) },
        parameters = 0
    ).value

private fun SqlDriver.setUserVersion(version: Long) {
    execute(identifier = null, sql = "PRAGMA user_version = $version", parameters = 0)
}