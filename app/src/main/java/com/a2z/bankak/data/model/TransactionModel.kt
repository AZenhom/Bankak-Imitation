package com.a2z.bankak.data.model

import java.util.Date

data class TransactionModel(
    val id: String? = null,
    val fromId: String? = null,
    val toId: String? = null,
    val toName: String? = null,
    val toMobile: String? = null,
    val comment: String? = null,
    val amount: Int? = null,
    val createdAt: Date? = null,
) : java.io.Serializable {
    companion object {
        const val FROM_ID = "fromId"
    }

    fun fromIdFormatted() = fromId?.chunked(4)?.joinToString(" ")

    fun toIdFormatted() = toId?.chunked(4)?.joinToString(" ")
}
