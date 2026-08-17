package com.dmsadjt.kotoba.ocr
import androidx.compose.runtime.mutableStateOf
import com.atilika.kuromoji.ipadic.Tokenizer

class JapaneseNormalizer {
    private val tokenizer = Tokenizer()

    fun extractLookupCandidates(raw: String) : List<String> {
        val results = mutableListOf<String>()
        val kanjiRegex = Regex("[\u4E00-\u9FFF]")

        for (token in tokenizer.tokenize(raw)) {
            if (results.size >= 3) break
            if (kanjiRegex.containsMatchIn(token.baseForm) && token.baseForm !in results) {
                results.add(token.baseForm)
            }
        }

        return results;
    }
}