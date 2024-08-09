package util

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

fun formatIsoStringToReadableDate(isoString: String): String {
    val localDateTime = LocalDateTime.parse(isoString)
    val dayOfWeek = localDateTime.dayOfWeek.name
    val dayOfMonth = localDateTime.dayOfMonth
    val month = localDateTime.month.name

    return "$dayOfWeek, $dayOfMonth $month"
}

fun dateCheck(isoString: String): String {
    val time = LocalDateTime.parse(isoString).date
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return when (time) {
        today -> "Today"
        today.plus(DatePeriod(days = 1)) -> "Tomorrow"
        else -> ""
    }
}