package com.a2z.bankak.core.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.getDateText(format: String): String {
    val df: DateFormat = SimpleDateFormat(format, Locale.US)
    return df.format(this)
}