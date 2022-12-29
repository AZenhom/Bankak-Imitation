package com.a2z.bankak.core.utils

import com.a2z.bankak.BuildConfig

class Constants {
    companion object{
        // Collections
        const val COLLECTION_USER = BuildConfig.COLLECTION_USER
        const val COLLECTION_TRANSACTION = BuildConfig.COLLECTION_TRANSACTION

        // Firestore document attributes
        const val CREATED_AT = "createdAt"
    }
}