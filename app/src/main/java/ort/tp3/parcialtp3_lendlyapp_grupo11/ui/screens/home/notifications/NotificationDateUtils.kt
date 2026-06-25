package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object NotificationDateUtils {
    private const val DATE_KEY_PATTERN = "yyyy-MM-dd"
    private const val TRANSACTION_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun currentMonth(): CalendarMonthUi {
        return monthFromCalendar(Calendar.getInstance())
    }

    fun todayDateKey(): String {
        return dateKeyFormat().format(Calendar.getInstance().time)
    }

    fun addMonths(month: CalendarMonthUi, amount: Int): CalendarMonthUi {
        val calendar = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, month.year)
            set(Calendar.MONTH, month.month)
            set(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MONTH, amount)
        }
        return monthFromCalendar(calendar)
    }

    fun dateUiFromKey(dateKey: String): CalendarDateUi {
        return CalendarDateUi(
            dateKey = dateKey,
            displayLabel = formatDateKey(dateKey, "EEE, MMM d")
        )
    }

    fun shortDateLabel(dateKey: String): String {
        return formatDateKey(dateKey, "MMM d")
    }

    fun loanDateKey(rawDate: String?): String? {
        return rawDate?.takeIf { it.isNotBlank() }?.take(10)
    }

    fun transactionDateKey(rawDate: String): String? {
        val parser = SimpleDateFormat(TRANSACTION_PATTERN, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val formatter = dateKeyFormat().apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        return try {
            formatter.format(parser.parse(rawDate) ?: return rawDate.take(10))
        } catch (e: Exception) {
            rawDate.takeIf { it.length >= 10 }?.take(10)
        }
    }

    fun transactionTimeLabel(rawDate: String): String {
        val parser = SimpleDateFormat(TRANSACTION_PATTERN, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val formatter = SimpleDateFormat("h:mm a", Locale.US)

        return try {
            formatter.format(parser.parse(rawDate) ?: return rawDate)
        } catch (e: Exception) {
            rawDate
        }
    }

    private fun monthFromCalendar(source: Calendar): CalendarMonthUi {
        val calendar = source.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val leadingEmptyDays = firstDayOfWeek - Calendar.SUNDAY
        val todayKey = todayDateKey()

        val days = mutableListOf<CalendarDayUi>()
        repeat(leadingEmptyDays) {
            days.add(CalendarDayUi(dayOfMonth = null, dateKey = null, isToday = false))
        }

        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dateKey = dateKeyFormat().format(calendar.time)
            days.add(
                CalendarDayUi(
                    dayOfMonth = day,
                    dateKey = dateKey,
                    isToday = dateKey == todayKey
                )
            )
        }

        while (days.size % 7 != 0) {
            days.add(CalendarDayUi(dayOfMonth = null, dateKey = null, isToday = false))
        }

        return CalendarMonthUi(
            year = year,
            month = month,
            title = SimpleDateFormat("MMMM yyyy", Locale.US).format(source.time),
            days = days
        )
    }

    private fun formatDateKey(dateKey: String, outputPattern: String): String {
        return try {
            val date = dateKeyFormat().parse(dateKey) ?: return dateKey
            SimpleDateFormat(outputPattern, Locale.US).format(date)
        } catch (e: Exception) {
            dateKey
        }
    }

    private fun dateKeyFormat(): SimpleDateFormat {
        return SimpleDateFormat(DATE_KEY_PATTERN, Locale.US)
    }
}
