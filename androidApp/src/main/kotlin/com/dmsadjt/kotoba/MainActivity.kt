package com.dmsadjt.kotoba

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dmsadjt.kotoba.screen.MemoShareScreen
import com.dmsadjt.kotoba.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == Intent.ACTION_SEND) getSharedWord()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (intent.action == Intent.ACTION_SEND) getSharedWord()
        setContent {
            val sharedWord = mainViewModel.sharedWord
            if (sharedWord != null) {
                MemoShareScreen(
                    word = sharedWord,
                    onDismiss = {
                        mainViewModel.clearSharedWord()
                        finish()
                    }
                )
            } else {
                App()
            }
        }
    }

    private fun getSharedWord() {
        val word = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (word != null) mainViewModel.updateSharedWord(word)
    }
}