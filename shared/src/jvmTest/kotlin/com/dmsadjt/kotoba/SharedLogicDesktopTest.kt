package com.dmsadjt.kotoba

import com.atilika.kuromoji.ipadic.Tokenizer
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedLogicDesktopTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun exploreTokenizer() {
        val tokenizer = Tokenizer()
        val tokens = tokenizer.tokenize("誇って良いものだ")

        for (token in tokens) {
            println("surface=${token.surface}" +
                    "baseform=${token.baseForm}" +
                    "pos1=${token.partOfSpeechLevel1}"
            )
        }


    }
}