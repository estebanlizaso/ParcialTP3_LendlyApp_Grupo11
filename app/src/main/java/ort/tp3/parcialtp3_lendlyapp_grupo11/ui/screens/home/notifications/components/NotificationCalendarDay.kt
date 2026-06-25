package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.CalendarDayUi
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun NotificationCalendarDay(
    day: CalendarDayUi,
    selected: Boolean,
    marked: Boolean,
    onClick: (CalendarDayUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val clickableModifier = if (day.dateKey != null) {
        Modifier.clickable { onClick(day) }
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .size(30.dp)
            .then(if (selected) Modifier.background(Green, CircleShape) else Modifier)
            .then(if (marked && !selected) Modifier.border(1.dp, Green, CircleShape) else Modifier)
            .then(if (day.isToday && !selected && !marked) Modifier.border(1.dp, GrayText, CircleShape) else Modifier)
            .then(clickableModifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.dayOfMonth?.toString().orEmpty(),
            color = if (day.dayOfMonth == null) Color.Transparent else BlackFont,
            fontSize = 13.sp,
            fontWeight = if (selected || marked) FontWeight.SemiBold else FontWeight.Normal,
            fontFamily = interFonts
        )
    }
}
