package com.dmsadjt.kotoba.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.formatEpochDate
import com.dmsadjt.kotoba.theme.TearDivider
import com.dmsadjt.kotoba.theme.VhsCard
import com.dmsadjt.kotoba.theme.VhsColors
import com.dmsadjt.kotoba.theme.VhsSectionHeader
import com.dmsadjt.kotoba.theme.hardShadow
import com.dmsadjt.kotoba.theme.spineColorFor
import com.dmsadjt.kotoba.theme.stampShape
import com.dmsadjt.kotoba.theme.ticketShape
import kotlin.random.Random

/**
 * Back-of-the-case catalog page for a single shelved tape. Everything shown
 * is derived from the memo itself; richer data can slot in here later.
 */
@Composable
fun MemoDetailScreen(memo: Memo, onBack: () -> Unit) {
    val spine = spineColorFor(memo.word)
    val kanjiCount = memo.word.count { it in '一'..'鿿' }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .hardShadow(stampShape(6.dp), offset = 3.dp)
                .background(VhsColors.Counter, stampShape(6.dp))
                .clickable(onClick = onBack)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                "◀ BACK TO SHELF",
                color = VhsColors.Cream,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        VhsCard(
            modifier = Modifier.fillMaxWidth(),
            fill = VhsColors.Cream,
            shape = ticketShape(18.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .width(14.dp)
                        .fillMaxHeight()
                        .background(spine)
                )
                Column(modifier = Modifier.weight(1f).padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "RENTAL CATALOG · TAPE #${memo.id.toString().padStart(4, '0')}",
                            color = VhsColors.Ink.copy(alpha = 0.55f),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            fontSize = 11.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .rotate(-8f)
                                .border(2.dp, VhsColors.Red, stampShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                "IN COLLECTION",
                                color = VhsColors.Red,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        memo.word,
                        color = VhsColors.Ink,
                        fontWeight = FontWeight.Black,
                        fontSize = 46.sp
                    )
                    Text(
                        memo.reading,
                        color = VhsColors.Teal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    TearDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    VhsSectionHeader("Synopsis")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        memo.meaning,
                        color = VhsColors.Ink.copy(alpha = 0.85f),
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DetailStamp("$kanjiCount KANJI")
                        DetailStamp("${memo.reading.length} MORA")
                        DetailStamp("RENTED ${formatEpochDate(memo.savedAt)}", fill = VhsColors.Amber)
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    TearDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Barcode(seed = memo.word.hashCode())
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "BE KIND, PLEASE REWIND",
                                color = VhsColors.Ink.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                fontSize = 10.sp
                            )
                            Text(
                                "RETURN TO: RANDOM PLAY VIDEO",
                                color = VhsColors.Ink.copy(alpha = 0.5f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailStamp(label: String, fill: Color = VhsColors.Paper) {
    Box(
        modifier = Modifier
            .hardShadow(stampShape(4.dp), offset = 2.dp)
            .background(fill, stampShape(4.dp))
            .border(2.dp, VhsColors.Ink, stampShape(4.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            label,
            color = VhsColors.Ink,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            fontSize = 11.sp
        )
    }
}

/** Decorative rental-sticker barcode; bar widths are seeded by the word. */
@Composable
private fun Barcode(seed: Int, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.width(110.dp).height(30.dp)) {
        val rnd = Random(seed)
        var x = 0f
        while (x < size.width) {
            val w = rnd.nextInt(2, 6).dp.toPx()
            if (rnd.nextInt(3) > 0) {
                drawRect(
                    color = VhsColors.Ink,
                    topLeft = Offset(x, 0f),
                    size = Size(w.coerceAtMost(size.width - x), size.height)
                )
            }
            x += w + 2.dp.toPx()
        }
    }
}
