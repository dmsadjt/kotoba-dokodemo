package com.dmsadjt.kotoba

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
@Composable
actual fun App() {
    MaterialTheme {
        var selectedScreen by remember { mutableStateOf("memos") }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedScreen == "memos",
                        onClick = { selectedScreen = "memos" },
                        icon = { Text("📝") },
                        label = { Text("Memos") }
                    )
                    NavigationBarItem(
                        selected = selectedScreen == "lookup",
                        onClick = { selectedScreen = "lookup" },
                        icon = { Text("🔍") },
                        label = { Text("Lookup") }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                when (selectedScreen) {
                    "memos" -> MemoListScreen()
                    "lookup" -> LookupScreen()
                }
            }
        }
    }
}