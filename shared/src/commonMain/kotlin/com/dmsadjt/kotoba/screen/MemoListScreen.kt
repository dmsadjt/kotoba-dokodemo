package com.dmsadjt.kotoba.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmsadjt.kotoba.Memo
import com.dmsadjt.kotoba.theme.VhsCard
import com.dmsadjt.kotoba.theme.VhsColors
import com.dmsadjt.kotoba.theme.hardShadow
import com.dmsadjt.kotoba.theme.spineColorFor
import com.dmsadjt.kotoba.theme.stampShape
import com.dmsadjt.kotoba.theme.ticketShape
import com.dmsadjt.kotoba.viewmodel.MemoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoListScreen(
    viewModel: MemoViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val memos by viewModel.memos.collectAsState()
    var selectedMemo by remember { mutableStateOf<Memo?>(null) }

    selectedMemo?.let { memo ->
        MemoDetailScreen(memo = memo, onBack = { selectedMemo = null })
        return
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "MY SHELF",
            color = VhsColors.Ink,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            fontSize = 22.sp
        )
        Text(
            "${memos.size} tape${if (memos.size == 1) "" else "s"} rented",
            color = VhsColors.Ink.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .hardShadow(stampShape(6.dp), offset = 3.dp)
                .background(VhsColors.Cream, stampShape(6.dp))
                .border(2.dp, VhsColors.Ink, stampShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            BasicTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                singleLine = true,
                textStyle = TextStyle(color = VhsColors.Ink, fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(memos) { memo ->
                MemoItem(memo, onClick = { selectedMemo = memo })
            }
        }
    }
}

@Composable
fun MemoItem(memo: Memo, onClick: () -> Unit = {}) {
    val spine = spineColorFor(memo.word)
    VhsCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        fill = VhsColors.Paper,
        shape = ticketShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(spine)
            )
            Column(modifier = Modifier.padding(14.dp)) {
                Text(memo.word, color = VhsColors.Ink, fontWeight = FontWeight.Medium, fontSize = 24.sp)
                Text(memo.reading, color = VhsColors.Teal, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(
                    memo.meaning,
                    color = VhsColors.Ink.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
