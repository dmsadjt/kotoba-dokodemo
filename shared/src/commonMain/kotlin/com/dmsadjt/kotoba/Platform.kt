package com.dmsadjt.kotoba

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform