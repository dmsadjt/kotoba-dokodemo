package com.dmsadjt.kotoba.db

fun createDatabase(driverFactory: DatabaseDriverFactory): KotobaDatabase {
    val driver = driverFactory.createDriver()
    return KotobaDatabase(driver)
}