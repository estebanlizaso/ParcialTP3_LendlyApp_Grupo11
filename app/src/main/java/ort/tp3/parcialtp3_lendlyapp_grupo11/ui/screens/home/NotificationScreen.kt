package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    var showCalendar by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            NotificationTopBar(
                onBackClick = onBackClick,
                onCalendarClick = { showCalendar = true }
            )

            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = "Notification",
                color = BlackFont,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )

            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                NotificationSection(title = "Today")
                NotificationItem(unread = true, title = "Your due date is almost here!")
                NotificationItem(unread = true, title = "Your due date is almost here!")
                NotificationItem(unread = false, title = "Got a minute to help us out?")
                NotificationItem(unread = false, title = "Got a minute to help us out?")

                Spacer(modifier = Modifier.height(14.dp))
                NotificationSection(title = "Announcement")
                NotificationItem(unread = true, title = "Your due date is almost here!")
                NotificationItem(unread = false, title = "Got a minute to help us out?")
            }
        }

        if (showCalendar) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000))
                    .clickable { showCalendar = false }
            )
            CalendarCard(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(top = 92.dp),
                onOkClick = { showCalendar = false }
            )
        }
    }
}

@Composable
private fun NotificationTopBar(
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "<",
            color = BlackFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .size(32.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
        CalendarIcon(onClick = onCalendarClick)
    }
}

@Composable
private fun CalendarIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 15.dp, height = 17.dp)
                .border(1.dp, BlackFont, RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color(0xFFEDEDED))
            )
        }
    }
}

@Composable
private fun NotificationSection(title: String) {
    Text(
        text = title,
        color = GrayText,
        fontSize = 13.sp,
        fontFamily = interFonts,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun NotificationItem(
    unread: Boolean,
    title: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(if (unread) Green else Color(0xFFE9E4E4))
        )

        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = BlackFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "Mar 8", color = GrayText, fontSize = 11.sp, fontFamily = interFonts,fontWeight = FontWeight.SemiBold,)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We'd like to remind you about your due date this month. Please pay this balance within the date to keep your credit score. Tap to pay.",
                color = GrayText,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontFamily = interFonts
            )
        }
    }
}

@Composable
private fun CalendarCard(
    modifier: Modifier = Modifier,
    onOkClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(top = 18.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(text = "Due dates", color = BlackFont, fontSize = 12.sp, fontFamily = interFonts,fontWeight = FontWeight.SemiBold,)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Mon, Aug 17",
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
                text = "August 2023  v",
                color = BlackFont,
                fontSize = 12.sp,
                fontFamily = interFonts,
                modifier = Modifier.weight(1f)
            )
            Text(text = "<", color = BlackFont, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = ">", color = BlackFont, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
        CalendarGrid()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "OK",
                color = BlackFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFCF8F8))
                    .clickable { onOkClick() }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun CalendarGrid() {
    val weeks = listOf(
        listOf("", "", "1", "2", "3", "4", "5"),
        listOf("6", "7", "8", "9", "10", "11", "12"),
        listOf("13", "14", "15", "16", "17", "18", "19"),
        listOf("20", "21", "22", "23", "24", "25", "26"),
        listOf("27", "28", "29", "30", "31", "", "")
    )

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                CalendarDay(text = day, header = true)
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        weeks.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                week.forEach { day ->
                    CalendarDay(
                        text = day,
                        selected = day == "17",
                        outlined = day == "5"
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
private fun CalendarDay(
    text: String,
    header: Boolean = false,
    selected: Boolean = false,
    outlined: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .then(if (selected) Modifier.background(Green, CircleShape) else Modifier)
            .then(if (outlined) Modifier.border(1.dp, Green, CircleShape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = BlackFont,
            fontSize = if (header) 12.sp else 13.sp,
            fontWeight = if (header) FontWeight.SemiBold else FontWeight.Normal,
            fontFamily = interFonts
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        NotificationScreen()
    }
}
