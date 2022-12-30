package com.a2z.bankak.data.model

data class UserModel(
    val id: String? = null,
    val idFull: String? = null,
    val name: String? = null,
    val type: String? = null,
    val branch: String? = null,
    var credit: Int? = null,
    val password: String? = null,
    val admin: Int? = null
) : java.io.Serializable {
    companion object {
        const val ID_FULL = "idFull"
    }
}