package com.a2z.bankak.core.utils

import java.text.NumberFormat
import java.util.*

fun Number.formatNumber(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}