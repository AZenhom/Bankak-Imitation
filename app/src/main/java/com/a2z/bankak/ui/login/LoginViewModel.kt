package com.a2z.bankak.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.repository.SettingsRepository
import com.hadilq.liveevent.LiveEvent
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    fun switchLanguage(context: Context): LiveData<Boolean> {
        val liveData = LiveEvent<Boolean>()
        safeLauncher {
            val currentLanguage = settingsRepository.getCurrentLanguage()
            val newLanguage = if (currentLanguage == "en") "ar" else "en"
            Lingver.getInstance().setLocale(context, newLanguage)
            settingsRepository.setCurrentLanguage(newLanguage)
            liveData.value = true
        }
        return liveData
    }
}