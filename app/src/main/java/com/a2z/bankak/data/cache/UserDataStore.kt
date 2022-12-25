package com.a2z.bankak.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.a2z.bankak.data.model.UserModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "userDataStore")

class UserDataStore @Inject constructor(@ApplicationContext val context: Context) {

    private val isSignedInPref = booleanPreferencesKey("isSignedInPref")
    private val userIdPref = stringPreferencesKey("userIdPref")
    private val userModelPref = stringPreferencesKey("adminModel")

    suspend fun removeAllPref() {
        context.userDataStore.edit {
            it.clear()
        }
    }

    suspend fun setSignedIn(value: Boolean) {
        context.userDataStore.edit {
            it[isSignedInPref] = value
        }
    }

    suspend fun isSignedIn(): Boolean {
        return context.userDataStore.data.map {
            it[isSignedInPref] ?: false
        }.first()
    }

    suspend fun setUserId(value: String) {
        context.userDataStore.edit {
            it[userIdPref] = value
        }
    }

    suspend fun getUserId(): String? {
        return context.userDataStore.data.map {
            it[userIdPref]
        }.first()
    }

    suspend fun setCurrentUserModel(model: UserModel?) {
        context.userDataStore.edit {
            val json: String = Gson().toJson(model, UserModel::class.java)
            it[userModelPref] = json
        }
    }

    suspend fun getCurrentUserModel(): UserModel? {
        return context.userDataStore.data.map {
            val json = it[userModelPref]
            return@map if (json == null)
                null
            else
                Gson().fromJson(json, UserModel::class.java)
        }.first()
    }

}