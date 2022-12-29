package com.a2z.bankak.data.repository

import com.a2z.bankak.core.utils.Constants
import com.a2z.bankak.data.base.BaseRepository
import com.a2z.bankak.data.cache.UserDataStore
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.model.response.ErrorModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val fireStoreDB: FirebaseFirestore,
    private val userDataStore: UserDataStore
) : BaseRepository() {

    suspend fun getUserById(userId: String): StatefulResult<UserModel> {
        return try {
            val document =
                fireStoreDB.collection(Constants.COLLECTION_USER).document(userId).get()
                    .await()
            StatefulResult.Success(document.toObject(UserModel::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }

    suspend fun getUserByIdFull(idFull: String): StatefulResult<UserModel> {
        return try {
            val document =
                fireStoreDB.collection(Constants.COLLECTION_USER)
                    .whereEqualTo(UserModel.ID_FULL, idFull)
                    .get().await().firstOrNull()
            StatefulResult.Success(document?.toObject(UserModel::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }

    suspend fun createUpdateUser(userModel: UserModel): StatefulResult<UserModel> {
        if (userModel.id.isNullOrEmpty())
            return StatefulResult.Error(ErrorModel.Unknown)
        return try {
            val userRef = fireStoreDB
                .collection(Constants.COLLECTION_USER)
                .document(userModel.id)
            userRef.set(userModel).await()
            StatefulResult.Success(userRef.get().await().toObject(UserModel::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            StatefulResult.Error(ErrorModel.Unknown)
        }
    }

    suspend fun getCurrentUserModel(): UserModel? = execute {
        return@execute userDataStore.getCurrentUserModel()
    }

    suspend fun setCurrentUserModel(value: UserModel?) = execute {
        userDataStore.setCurrentUserModel(value)
    }

    suspend fun isSignedIn(): Boolean = execute {
        return@execute userDataStore.isSignedIn()
    }

    suspend fun setSignedIn(value: Boolean) = execute {
        userDataStore.setSignedIn(value)
    }

    suspend fun getUserId(): String? = execute {
        return@execute userDataStore.getUserId()
    }

    suspend fun setUserId(value: String?) = execute {
        userDataStore.setUserId(value ?: return@execute)
    }

    suspend fun removeAllPref() = execute {
        return@execute userDataStore.removeAllPref()
    }
}