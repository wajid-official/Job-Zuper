package zuper.dev.android.dashboard.snippet

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


fun Int.getPercent(value: Int): Float {
    return (value.toFloat() / this.toFloat())
}

fun getTodayDate(): String {
    val currentDate = LocalDate.now()

    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val month = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dayOfMonth = currentDate.dayOfMonth

    val dayOfMonthSuffix = when {
        dayOfMonth in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    return "$dayOfWeek, $month $dayOfMonth$dayOfMonthSuffix ${currentDate.year}"
}

fun String.getRoute(argument: String, placeHolder: String) = this.replace(placeHolder, argument)

fun String.isToday(): Boolean {
    return try {
        val dateToCheck = this.getDate()
        if (dateToCheck == null) {
            false
        } else {
            val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val firstFormattedDate = sdf.format(dateToCheck)
            val secondFormattedDate = sdf.format(Date())
            firstFormattedDate == secondFormattedDate
        }
    } catch (e: Exception) {
        false
    }
}

fun String.isSameDayAs(date: String): Boolean {
    try {
        val firstDate = this.getDate()
        val secondDate = date.getDate()
        return if (firstDate == null || secondDate == null) {
            false
        } else {
            val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

            val firstFormattedDate = sdf.format(firstDate)
            val secondFormattedDate = sdf.format(secondDate)

            firstFormattedDate == secondFormattedDate
        }
    } catch (e: Exception) {
        return false
    }
}

fun String.getDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.parse(this)
}

fun String.convertDate(inputFormat: String, outputFormat: String): String {
    return try {
        val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())

        val date = inputFormatter.parse(this)
        outputFormatter.format(date)
    } catch (e: Exception) {
        ""
    }
}