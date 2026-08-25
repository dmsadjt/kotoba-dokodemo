package com.dmsadjt.kotoba.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.srs.Grade
import com.dmsadjt.kotoba.theme.VhsCard
import com.dmsadjt.kotoba.theme.VhsColors
import com.dmsadjt.kotoba.theme.hardShadow
import com.dmsadjt.kotoba.theme.stampShape
import com.dmsadjt.kotoba.theme.ticketShape
import com.dmsadjt.kotoba.viewmodel.ReviewViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "REVIEW ROOM",
            color = VhsColors.Ink,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            fontSize = 22.sp
        )

        val memo = viewModel.currentMemo
        Text(
            if (memo != null) "${viewModel.dueCount} tape${if (viewModel.dueCount == 1) "" else "s"} left to watch" else "nothing due right now",
            color = VhsColors.Ink.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (memo == null) {
                EmptyReviewState()
            } else {
                ReviewCard(
                    memo = memo,
                    isFlipped = viewModel.isFlipped,
                    onFlip = { viewModel.flip() },
                    onGrade = { viewModel.grade(it) }
                )
            }
        }
    }
}

@Composable
private fun EmptyReviewState() {
    VhsCard(
        modifier = Modifier.padding(24.dp),
        fill = VhsColors.Paper,
        shape = ticketShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "NO TAPES DUE",
                color = VhsColors.Ink,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "come back later — the shelf is all caught up",
                color = VhsColors.Ink.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun ReviewCard(
    memo: Memo,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onGrade: (Grade) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        VhsCard(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .aspectRatio(1f)
                .clickable(enabled = !isFlipped, onClick = onFlip),
            fill = VhsColors.Paper,
            shape = ticketShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    memo.word,
                    color = VhsColors.Ink,
                    fontWeight = FontWeight.Medium,
                    fontSize = 48.sp
                )

                if (isFlipped) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        memo.reading,
                        color = VhsColors.Teal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        memo.meaning,
                        color = VhsColors.Ink.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "tap to flip",
                        color = VhsColors.Ink.copy(alpha = 0.4f),
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        if (isFlipped) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GradeButton("AGAIN", VhsColors.RedDark, onClick = { onGrade(Grade.AGAIN) })
                GradeButton("HARD", VhsColors.Amber, onClick = { onGrade(Grade.HARD) })
                GradeButton("GOOD", VhsColors.Teal, onClick = { onGrade(Grade.GOOD) })
                GradeButton("EASY", VhsColors.Red, onClick = { onGrade(Grade.EASY) })
            }
        }
    }
}

@Composable
private fun GradeButton(label: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .hardShadow(stampShape(6.dp), offset = 3.dp)
            .background(color, stampShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            label,
            color = VhsColors.Cream,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp,
            fontSize = 12.sp
        )
    }
}
