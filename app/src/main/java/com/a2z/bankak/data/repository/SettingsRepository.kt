package com.a2z.bankak.data.repository

import com.a2z.bankak.data.base.BaseRepository
import com.a2z.bankak.data.cache.SettingsDataStore
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : BaseRepository() {

    suspend fun getCurrentLanguage(): String = execute {
        return@execute settingsDataStore.getLanguage()
    }

    suspend fun setCurrentLanguage(value: String) = execute {
        settingsDataStore.setLanguage(value)
    }
}