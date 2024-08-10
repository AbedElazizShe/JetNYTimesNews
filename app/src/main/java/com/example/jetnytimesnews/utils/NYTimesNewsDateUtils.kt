package com.example.jetnytimesnews.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val inputFormat: SimpleDateFormat
    get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)

fun formatDate(date: String): Date {
    return inputFormat.parse(date) ?: Date()
}
