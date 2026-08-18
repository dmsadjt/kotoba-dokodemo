package com.dmsadjt.kotoba.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmsadjt.kotoba.theme.TearDivider
import com.dmsadjt.kotoba.theme.VhsCard
import com.dmsadjt.kotoba.theme.VhsColors
import com.dmsadjt.kotoba.theme.hardShadow
import com.dmsadjt.kotoba.theme.stampShape
import com.dmsadjt.kotoba.theme.ticketShape
import com.dmsadjt.kotoba.viewmodel.LookupViewModel
import androidx.compose.material3.Text
import org.koin.compose.koinInject

@Composable
fun LookupScreen(
    viewModel: LookupViewModel = koinInject()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "LOOK UP A TITLE",
            color = VhsColors.Ink,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            fontSize = 22.sp
        )
        Text(
            "search the shelves for a word",
            color = VhsColors.Ink.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        VhsCard(
            modifier = Modifier.fillMaxWidth(),
            fill = VhsColors.Cream,
            shape = ticketShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VhsColors.Paper, stampShape(6.dp))
                        .border(2.dp, VhsColors.Ink, stampShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    BasicTextField(
                        value = viewModel.searchQuery,
                        onValueChange = { viewModel.onQueryChange(it) },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = VhsColors.Ink,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .hardShadow(stampShape(6.dp), offset = 3.dp)
                        .background(VhsColors.Red, stampShape(6.dp))
                        .border(2.dp, VhsColors.Ink, stampShape(6.dp))
                        .clickable { viewModel.lookupWord(viewModel.searchQuery) }
                        .padding(vertical = 12.dp),
                ) {
                    Text(
                        "▷ RENT THIS WORD",
                        color = VhsColors.Cream,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        viewModel.searchResult?.let { entry ->
            var isSaved by remember(entry.id) { mutableStateOf(false) }

            VhsCard(
                modifier = Modifier.fillMaxWidth(),
                fill = VhsColors.Paper,
                shape = ticketShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "NOW RENTING",
                        color = VhsColors.Red,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        entry.word,
                        color = VhsColors.Ink,
                        fontWeight = FontWeight.Black,
                        fontSize = 38.sp
                    )
                    Text(
                        entry.reading,
                        color = VhsColors.Teal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TearDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        entry.meaning,
                        color = VhsColors.Ink.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .hardShadow(stampShape(6.dp), offset = 3.dp)
                            .background(if (isSaved) VhsColors.Teal else VhsColors.Amber, stampShape(6.dp))
                            .border(2.dp, VhsColors.Ink, stampShape(6.dp))
                            .clickable(enabled = !isSaved) {
                                viewModel.saveMemo(entry)
                                isSaved = true
                            }
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            if (isSaved) "✓ ADDED TO SHELF" else "＋ ADD TO SHELF",
                            color = VhsColors.Ink,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
