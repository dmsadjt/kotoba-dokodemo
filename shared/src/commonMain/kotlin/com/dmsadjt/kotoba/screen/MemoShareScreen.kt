package com.dmsadjt.kotoba.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dmsadjt.kotoba.viewmodel.MemoShareViewModel
import com.dmsadjt.kotoba.viewmodel.ShareResult
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoShareScreen(
    word: String,
    onDismiss: () -> Unit,
    viewModel: MemoShareViewModel = koinViewModel()
) {
    val result by viewModel.result.collectAsState()

    LaunchedEffect(word) {
        viewModel.handleSharedWord(word)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (val r = result) {
            is ShareResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ShareResult.Success -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (r.isNew) "Saved" else "Already saved",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(r.memo.word, style = MaterialTheme.typography.displaySmall)
                        Text(r.memo.reading, style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(r.memo.meaning, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                            Text("OK")
                        }
                    }
                }
            }
            is ShareResult.NotFound -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Not found", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "\"$word\" wasn't found in the dictionary",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}