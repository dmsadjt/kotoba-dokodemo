package com.dmsadjt.kotoba.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService

class AndroidClipboardHandler(private val context: Context) : ClipboardHandler{
    override fun copy(text: String) {
        val clipboard = context.getSystemService<ClipboardManager>()
        val clip = ClipData.newPlainText("kotoba", text)
        clipboard?.setPrimaryClip(clip)
    }
}