package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.Locale
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

class NotificationViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private var itemsByDate: Map<String, List<NotificationDayItemUi>> = emptyMap()

    init {
        loadNotifications()
    }

    fun onCalendarClick() {
        _uiState.value = _uiState.value.copy(isCalendarVisible = true)
    }

    fun onDismissCalendar() {
        _uiState.value = _uiState.value.copy(isCalendarVisible = false)
    }

    fun onPreviousMonthClick() {
        _uiState.value = _uiState.value.copy(
            visibleMonth = NotificationDateUtils.addMonths(_uiState.value.visibleMonth, -1)
        )
    }

    fun onNextMonthClick() {
        _uiState.value = _uiState.value.copy(
            visibleMonth = NotificationDateUtils.addMonths(_uiState.value.visibleMonth, 1)
        )
    }

    fun onDateSelected(day: CalendarDayUi) {
        val dateKey = day.dateKey ?: return
        _uiState.value = _uiState.value.copy(
            isCalendarVisible = false,
            selectedDate = NotificationDateUtils.dateUiFromKey(dateKey),
            selectedDayItems = itemsByDate[dateKey].orEmpty()
        )
    }

    fun onNotificationSelected(item: NotificationDayItemUi) {
        _uiState.value = _uiState.value.copy(
            selectedDate = NotificationDateUtils.dateUiFromKey(item.dateKey),
            selectedDayItems = itemsByDate[item.dateKey].orEmpty()
        )
    }

    fun onDismissDayDialog() {
        _uiState.value = _uiState.value.copy(
            selectedDate = null,
            selectedDayItems = emptyList()
        )
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val transactions = repository.getTransactions().transactions.mapNotNull { transaction ->
                    transaction.toNotificationItem()
                }
                val loanDueDates = repository.getLoans().loans.mapNotNull { loan ->
                    loan.toDueDateNotificationItem()
                }
                val items = (transactions + loanDueDates).sortedWith(
                    compareBy<NotificationDayItemUi> { it.dateKey }.thenBy { it.timeLabel }
                )

                itemsByDate = items.groupBy { it.dateKey }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    notifications = items,
                    markedDateKeys = itemsByDate.keys
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron cargar las notificaciones"
                )
            }
        }
    }

    private fun TransactionDto.toNotificationItem(): NotificationDayItemUi? {
        val dateKey = NotificationDateUtils.transactionDateKey(date) ?: return null

        return NotificationDayItemUi(
            id = id,
            dateKey = dateKey,
            dateLabel = NotificationDateUtils.shortDateLabel(dateKey),
            timeLabel = NotificationDateUtils.transactionTimeLabel(date),
            title = titleForType(),
            subtitle = merchantFromTitle().ifBlank { description.ifBlank { title } },
            amount = formatMoney(amount, currency),
            type = type.toNotificationType(amount)
        )
    }

    private fun LoanDto.toDueDateNotificationItem(): NotificationDayItemUi? {
        val dateKey = NotificationDateUtils.loanDateKey(nextPaymentDate) ?: return null

        return NotificationDayItemUi(
            id = "loan_due_$id",
            dateKey = dateKey,
            dateLabel = NotificationDateUtils.shortDateLabel(dateKey),
            timeLabel = nextPaymentLabel ?: "Due date",
            title = "Loan payment due",
            subtitle = "$purpose - $lender",
            amount = formatMoney(installmentAmount, "PHP"),
            type = NotificationDayItemType.LOAN_DUE_DATE
        )
    }

    private fun TransactionDto.titleForType(): String {
        return when (type) {
            "LOAN_PAYMENT" -> "Paid this month"
            "CASH_IN" -> "Added balance"
            "LOAN_DISBURSEMENT" -> "Loan approved"
            else -> description.ifBlank { title }
        }
    }

    private fun TransactionDto.merchantFromTitle(): String {
        return title.substringAfter("\u2014", "").trim()
    }

    private fun String.toNotificationType(amount: Double): NotificationDayItemType {
        return when (this) {
            "CASH_IN",
            "LOAN_DISBURSEMENT" -> NotificationDayItemType.ADDED_BALANCE
            "LOAN_PAYMENT" -> NotificationDayItemType.PAYMENT
            else -> if (amount >= 0) NotificationDayItemType.ADDED_BALANCE else NotificationDayItemType.PAYMENT
        }
    }

    private fun formatMoney(value: Double, currency: String): String {
        return String.format(Locale.US, "%,.2f %s", abs(value), currency)
    }
}
