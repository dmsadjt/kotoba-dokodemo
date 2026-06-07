package com.dmsadjt.kotoba

import android.content.Context

class AndroidDictionaryDataSource(context: Context) : DictionaryDataSource {
    override fun lookup(word: String) : DictionaryEntry? {
        return null
    }
}