package com.a2z.bankak.data.model

data class UserModel(
    val id: String? = null,
    val idFull: String? = null,
    val name: String? = null,
    val type: String? = null,
    val branch: String? = null
) : java.io.Serializable