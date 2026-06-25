package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppSearchBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.HomeTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun HistoryScreen(
    uiState: HistoryUiState,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf(HistoryFilterUi.ALL) }

    val filteredTransactions = remember(searchQuery, selectedFilter, uiState.todayTransactions) {
        uiState.todayTransactions.filterBy(searchQuery, selectedFilter)
    }
    val filteredLoans = remember(searchQuery, uiState.recentLoans) {
        uiState.recentLoans.filterBy(searchQuery)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            HomeTopBar(
                avatarUrl = uiState.avatarUrl,
                modifier = Modifier.padding(horizontal = 24.dp),
                onNotificationClick = onNotificationClick
            )

            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "History",
                color = BlackFont,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                AppSearchBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            HistoryFilterRow(
                filters = uiState.filters,
                selectedFilter = selectedFilter,
                onFilterClick = { selectedFilter = it },
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider(color = Color(0xFFE6E6E6), modifier = Modifier.padding(horizontal = 24.dp))

            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                HistorySectionTitle(title = "Today")
            }
            HorizontalDivider(color = Color(0xFFE6E6E6), modifier = Modifier.padding(horizontal = 24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                filteredTransactions.forEach { transaction ->
                    HistoryTransactionItem(
                        transaction = transaction,
                        onClick = { onTransactionClick(transaction.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                HistorySectionTitle(title = "Recent Loans")
            }
            HorizontalDivider(color = Color(0xFFE6E6E6), modifier = Modifier.padding(horizontal = 24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                filteredLoans.forEach { loan ->
                    RecentLoanHistoryItem(loan = loan)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HistoryFilterRow(
    filters: List<HistoryFilterUi>,
    selectedFilter: HistoryFilterUi,
    onFilterClick: (HistoryFilterUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            HistoryFilterChip(
                label = filter.label,
                selected = filter == selectedFilter,
                onClick = { onFilterClick(filter) }
            )
        }
    }
}

@Composable
private fun HistoryFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) Green else Color.White)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = if (selected) Green else Color(0xFF8E8E8E),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = BlackFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )
    }
}

@Composable
private fun HistorySectionTitle(title: String) {
    Text(
        text = title,
        color = GrayText,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = interFonts,
        modifier = Modifier.padding(top = 20.dp, bottom = 18.dp)
    )
}

@Composable
private fun HistoryTransactionItem(
    transaction: HistoryTransactionUi,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        HistoryIcon(imageResId = transaction.type.toIconResId(), contentDescription = transaction.title)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 18.dp)
        ) {
            Text(
                text = transaction.time,
                color = GrayText,
                fontSize = 13.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = transaction.title,
                color = BlackFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            if (transaction.merchant.isNotBlank()) {
                Text(
                    text = transaction.merchant,
                    color = GrayText,
                    fontSize = 13.sp,
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = transaction.amount,
                color = BlackFont,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun RecentLoanHistoryItem(loan: RecentLoanHistoryUi) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HistoryIcon(imageResId = R.drawable.history_check, contentDescription = loan.status)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 18.dp)
        ) {
            Text(
                text = loan.date,
                color = GrayText,
                fontSize = 13.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = loan.productName,
                color = BlackFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = loan.merchant,
                color = GrayText,
                fontSize = 13.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = loan.status,
                color = BlackFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts,
                textAlign = TextAlign.End
            )
        }
    }
}

private fun List<HistoryTransactionUi>.filterBy(
    query: String,
    filter: HistoryFilterUi
): List<HistoryTransactionUi> {
    return filter { transaction ->
        transaction.matchesQuery(query) && transaction.matchesFilter(filter)
    }
}

private fun List<RecentLoanHistoryUi>.filterBy(query: String): List<RecentLoanHistoryUi> {
    return filter { loan ->
        val normalizedQuery = query.trim().lowercase()
        normalizedQuery.isEmpty() ||
            loan.date.lowercase().contains(normalizedQuery) ||
            loan.productName.lowercase().contains(normalizedQuery) ||
            loan.merchant.lowercase().contains(normalizedQuery) ||
            loan.status.lowercase().contains(normalizedQuery)
    }
}

private fun HistoryTransactionUi.matchesQuery(query: String): Boolean {
    val normalizedQuery = query.trim().lowercase()
    return normalizedQuery.isEmpty() ||
        time.lowercase().contains(normalizedQuery) ||
        title.lowercase().contains(normalizedQuery) ||
        merchant.lowercase().contains(normalizedQuery) ||
        amount.lowercase().contains(normalizedQuery)
}

private fun HistoryTransactionUi.matchesFilter(filter: HistoryFilterUi): Boolean {
    return when (filter) {
        HistoryFilterUi.ALL,
        HistoryFilterUi.TYPE,
        HistoryFilterUi.BALANCE -> true
        HistoryFilterUi.PAID_BILLS -> type == HistoryTransactionType.PAID_BILL
        HistoryFilterUi.ADDED -> type == HistoryTransactionType.ADDED_BALANCE
    }
}

private fun HistoryTransactionType.toIconResId(): Int {
    return when (this) {
        HistoryTransactionType.PAID_BILL -> R.drawable.history_payment
        HistoryTransactionType.ADDED_BALANCE -> R.drawable.history_add
    }
}

@Composable
private fun HistoryIcon(
    imageResId: Int,
    contentDescription: String
) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        HistoryScreen(uiState = sampleHistoryUiState())
    }
}
