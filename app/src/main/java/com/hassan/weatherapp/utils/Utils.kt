package com.hassan.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Int): String {
    val date = Date(timestamp * 1000L)
    val format = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault())
    return format.format(date)
}

fun formatDecimal(value: Double): String {
    return String.format("%.1f", value)
}

fun formatTime(timestamp: Int): String {
    val date = Date(timestamp * 1000L)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date)
}

fun getWeekdayFromTimestamp(timestamp: Int): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000L

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sunday"
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        else -> "Unknown"
    }
}