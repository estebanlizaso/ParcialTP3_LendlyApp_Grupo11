package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.CalendarDateUi
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.CalendarDayUi
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.CalendarMonthUi
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun NotificationCalendarCard(
    month: CalendarMonthUi,
    selectedDate: CalendarDateUi?,
    markedDateKeys: Set<String>,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (CalendarDayUi) -> Unit,
    onOkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(top = 18.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Due dates",
                color = BlackFont,
                fontSize = 12.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = selectedDate?.displayLabel ?: "Select a day",
                color = BlackFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE5E0E0))
        )
        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = month.title,
                color = BlackFont,
                fontSize = 12.sp,
                fontFamily = interFonts,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "<",
                color = BlackFont,
                fontSize = 18.sp,
                modifier = Modifier.clickable { onPreviousMonthClick() }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = ">",
                color = BlackFont,
                fontSize = 18.sp,
                modifier = Modifier.clickable { onNextMonthClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        NotificationCalendarGrid(
            month = month,
            selectedDate = selectedDate,
            markedDateKeys = markedDateKeys,
            onDateClick = onDateClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onOkClick) {
                Text(
                    text = "OK",
                    color = BlackFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun NotificationCalendarGrid(
    month: CalendarMonthUi,
    selectedDate: CalendarDateUi?,
    markedDateKeys: Set<String>,
    onDateClick: (CalendarDayUi) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Box(modifier = Modifier.width(30.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = day,
                        color = GrayText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = interFonts
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        month.days.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                week.forEach { day ->
                    NotificationCalendarDay(
                        day = day,
                        selected = day.dateKey != null && day.dateKey == selectedDate?.dateKey,
                        marked = day.dateKey != null && markedDateKeys.contains(day.dateKey),
                        onClick = onDateClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
