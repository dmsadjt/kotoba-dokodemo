package com.dmsadjt.kotoba

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.dmsadjt.kotoba.ocr.ClipboardWatcher
import com.dmsadjt.kotoba.screen.LookupScreen
import com.dmsadjt.kotoba.screen.MemoListScreen
import com.dmsadjt.kotoba.theme.VhsCard
import com.dmsadjt.kotoba.theme.VhsColors
import com.dmsadjt.kotoba.theme.TearDivider
import com.dmsadjt.kotoba.theme.hardShadow
import com.dmsadjt.kotoba.theme.stampShape
import com.dmsadjt.kotoba.theme.ticketShape
import com.dmsadjt.kotoba.viewmodel.OcrLookupViewModel
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import java.awt.Toolkit

@Composable
actual fun App() {
    MaterialTheme {
        var selectedScreen by remember { mutableStateOf("lookup") }
        val clipboardWatcher = koinInject<ClipboardWatcher>()
        var isWatching by remember { mutableStateOf(false) }

        val ocrLookupViewModel = koinInject<OcrLookupViewModel>()
        val searchResult = ocrLookupViewModel.searchResult

        if (searchResult.isNotEmpty()) {
            LaunchedEffect(searchResult) {
                delay(6000)
                ocrLookupViewModel.clearResults()
            }

            // Start at an absolute position (not Alignment.Center) so WindowState.position
            // is always a WindowPosition.Absolute we can offset while dragging.
            val screenSize = remember { Toolkit.getDefaultToolkit().screenSize }
            val popupWidth = 340
            val popupHeight = (140 + searchResult.size.coerceAtMost(3) * 90).coerceAtMost(420)
            val ocrWindowState = rememberWindowState(
                size = DpSize(popupWidth.dp, popupHeight.dp),
                position = WindowPosition(
                    x = ((screenSize.width - popupWidth) / 2).dp,
                    y = ((screenSize.height - popupHeight) / 2).dp
                )
            )

            Window(
                onCloseRequest = { ocrLookupViewModel.clearResults() },
                state = ocrWindowState,
                focusable = false,
                alwaysOnTop = true,
                undecorated = true,
                resizable = false,
                title = "Kotoba Lookup"
            ) {
                OcrResultTicket(
                    entries = searchResult,
                    onDismiss = { ocrLookupViewModel.clearResults() },
                    onSave = { entry -> ocrLookupViewModel.saveMemo(entry)},
                    windowState = ocrWindowState
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(VhsColors.Paper)
        ) {
            // "Counter" sidebar - the video store front desk
            Column(
                modifier = Modifier
                    .width(210.dp)
                    .fillMaxHeight()
                    .background(VhsColors.Counter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "RANDOM PLAY",
                    color = VhsColors.Amber,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    "kotoba video · dict. counter",
                    color = VhsColors.Cream.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StoreNavItem(
                    label = "Lookup",
                    selected = selectedScreen == "lookup",
                    onClick = { selectedScreen = "lookup" }
                )
                StoreNavItem(
                    label = "My Shelf",
                    selected = selectedScreen == "memos",
                    onClick = { selectedScreen = "memos" }
                )

                Spacer(modifier = Modifier.height(4.dp))
                TearDivider(color = VhsColors.Cream.copy(alpha = 0.25f))
                Spacer(modifier = Modifier.height(4.dp))

                RecordingToggle(
                    isWatching = isWatching,
                    onToggle = {
                        isWatching = !isWatching
                        if (isWatching) clipboardWatcher.start() else clipboardWatcher.stop()
                    }
                )
            }

            // Content - the shop floor: lit from above, with the counter
            // casting a soft shadow along the left seam.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(listOf(VhsColors.Paper, VhsColors.PaperDark))
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(16.dp)
                        .background(
                            Brush.horizontalGradient(listOf(VhsColors.Shadow, Color.Transparent))
                        )
                )
                Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    when (selectedScreen) {
                        "lookup" -> LookupScreen()
                        "memos" -> MemoListScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun StoreNavItem(label: String, selected: Boolean, onClick: () -> Unit) {
    val fill = if (selected) VhsColors.Red else VhsColors.CounterLight
    val textColor = if (selected) VhsColors.Cream else VhsColors.Cream.copy(alpha = 0.8f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .hardShadow(stampShape(6.dp), VhsColors.ShadowDark, 3.dp)
            .background(fill, stampShape(6.dp))
            .border(1.dp, VhsColors.Ink.copy(alpha = if (selected) 1f else 0f), stampShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            label.uppercase(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontSize = 13.sp
        )
    }
}

@Composable
fun RecordingToggle(isWatching: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .hardShadow(stampShape(6.dp), VhsColors.ShadowDark, 3.dp)
            .background(if (isWatching) VhsColors.RedDark else VhsColors.CounterLight, stampShape(6.dp))
            .clickable(onClick = onToggle)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(9.dp)
                .background(if (isWatching) VhsColors.Amber else VhsColors.Cream.copy(alpha = 0.3f), CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            if (isWatching) "● REC — WATCHING" else "○ STANDBY",
            color = VhsColors.Cream,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            fontSize = 12.sp
        )
    }
}

@Composable
fun OcrResultTicket(entries: List<DictionaryEntry>, onDismiss: () -> Unit, onSave: (DictionaryEntry) -> Unit, windowState: WindowState) {
    val density = LocalDensity.current
    val savedIds = remember { mutableStateListOf<Long>() }

    // Double frame - thick outer wall plus a thin inner lip, like the
    // moulded tray inside a cassette case.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VhsColors.Cream)
            .border(3.dp, VhsColors.Ink)
            .padding(5.dp)
            .border(1.dp, VhsColors.Ink.copy(alpha = 0.25f))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Drag by the header, same as grabbing a window title bar - this window is
        // undecorated (no native title bar), so dragging has to be done by hand.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val current = windowState.position as? WindowPosition.Absolute
                            ?: return@detectDragGestures
                        windowState.position = WindowPosition(
                            x = current.x + with(density) { dragAmount.x.toDp() },
                            y = current.y + with(density) { dragAmount.y.toDp() }
                        )
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "NOW SHOWING",
                color = VhsColors.Red,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .background(VhsColors.Ink, CircleShape)
                    .clickable(onClick = onDismiss)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("✕", color = VhsColors.Cream, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TearDivider()
        Spacer(modifier = Modifier.height(10.dp))

        entries.forEach { entry ->
            VhsCard(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                accent = VhsColors.Ink,
                fill = VhsColors.Paper,
                shape = ticketShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        entry.word,
                        color = VhsColors.Ink,
                        fontWeight = FontWeight.Black,
                        fontSize = 26.sp
                    )
                    Text(
                        entry.reading,
                        color = VhsColors.Teal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        entry.meaning,
                        color = VhsColors.Ink.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .hardShadow(stampShape(6.dp), offset = 3.dp)
                            .background(
                                if (entry.id in savedIds) VhsColors.Teal else VhsColors.Amber,
                                stampShape(6.dp)
                            )
                            .border(2.dp, VhsColors.Ink, stampShape(6.dp))
                            .clickable(enabled = entry.id !in savedIds) {
                                onSave(entry)
                                savedIds.add(entry.id)
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Text(
                            if (entry.id in savedIds) "✓ ADDED" else "＋ ADD TO SHELF",
                            color = VhsColors.Ink,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            fontSize = 11.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
