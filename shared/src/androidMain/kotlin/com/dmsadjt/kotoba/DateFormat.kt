package com.dmsadjt.kotoba

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun formatEpochDate(epochMillis: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date(epochMillis)).uppercase()
