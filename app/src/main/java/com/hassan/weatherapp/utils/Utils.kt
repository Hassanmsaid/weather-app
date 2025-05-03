package com.hassan.weatherapp.utils

import java.text.SimpleDateFormat
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