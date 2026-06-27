package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications

data class NotificationUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val isCalendarVisible: Boolean = false,
    val visibleMonth: CalendarMonthUi = NotificationDateUtils.currentMonth(),
    val selectedDate: CalendarDateUi? = null,
    val selectedDayItems: List<NotificationDayItemUi> = emptyList(),
    val markedDateKeys: Set<String> = emptySet(),
    val notifications: List<NotificationDayItemUi> = emptyList(),
    val todayDateKey: String = NotificationDateUtils.todayDateKey()
)

data class CalendarMonthUi(
    val year: Int,
    val month: Int,
    val title: String,
    val days: List<CalendarDayUi>
)

data class CalendarDayUi(
    val dayOfMonth: Int?,
    val dateKey: String?,
    val isToday: Boolean
)

data class CalendarDateUi(
    val dateKey: String,
    val displayLabel: String
)

data class NotificationDayItemUi(
    val id: String,
    val dateKey: String,
    val dateLabel: String,
    val timeLabel: String,
    val title: String,
    val subtitle: String,
    val amount: String?,
    val type: NotificationDayItemType,
    val logoResId: Int? = null,
    val logoUrl: String? = null
)

enum class NotificationDayItemType {
    ADDED_BALANCE,
    PAYMENT,
    LOAN_DUE_DATE
}
