package com.a2z.bankak.data.repository

import com.a2z.bankak.core.utils.Constants
import com.a2z.bankak.data.base.BaseRepository
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.data.model.response.ErrorModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val fireStoreDB: FirebaseFirestore,
) : BaseRepository() {

    suspend fun createTransactions(transactionModel: TransactionModel): StatefulResult<Unit> {
        if (transactionModel.id.isNullOrEmpty())
            return StatefulResult.Error(ErrorModel.Unknown)
        return try {
            val productRef = fireStoreDB
                .collection(Constants.COLLECTION_TRANSACTION)
                .document(transactionModel.id)
            productRef.set(transactionModel).await()
            StatefulResult.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }

    suspend fun getUserTransactions(
        userID: String,
        dateFrom: Date? = null,
        dateTo: Date? = null,
        limitTo: Int? = null
    ): StatefulResult<List<TransactionModel>> {
        return try {
            val reference = fireStoreDB.collection(Constants.COLLECTION_TRANSACTION)
            var query = reference.whereEqualTo(TransactionModel.FROM_ID, userID)
            dateFrom?.let {
                query = query.whereGreaterThanOrEqualTo(Constants.CREATED_AT, it)
            }
            dateTo?.let {
                query = query.whereLessThanOrEqualTo(Constants.CREATED_AT, it)
            }
            query = query.orderBy(Constants.CREATED_AT, Query.Direction.DESCENDING)
            limitTo?.let {
                query = query.limit(limitTo.toLong())
            }
            val documents = query.get().await()
            StatefulResult.Success(documents.toObjects(TransactionModel::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }

    suspend fun deleteTransaction(transactionModel: TransactionModel): StatefulResult<Unit> {
        if (transactionModel.id.isNullOrEmpty())
            return StatefulResult.Error(ErrorModel.Unknown)
        return try {
            val productRef = fireStoreDB
                .collection(Constants.COLLECTION_TRANSACTION)
                .document(transactionModel.id)
            productRef.delete().await()
            StatefulResult.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }
}