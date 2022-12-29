package com.a2z.bankak.ui.main_menu

import android.content.Context
import androidx.lifecycle.LiveData
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.a2z.bankak.data.repository.SettingsRepository
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    private val _onLogOutLiveData: LiveEvent<Boolean> = LiveEvent()
    val onLogOutLiveData: LiveData<Boolean> get() = _onLogOutLiveData

    init {
        updateCurrentRetailerModel()
    }

    fun getMenuItems(context: Context): List<MainMenuAdapter.MainMenuItem> {
        val menuItems = mutableListOf<MainMenuAdapter.MainMenuItem>()
        val textArray = context.resources.getStringArray(R.array.home_menuslv)
        val resourcesArray = listOf(
            R.drawable.accountsummary,
            R.drawable.billpayments,
            R.drawable.fundstransfer,
            R.drawable.cardlesswithdrawal,
            R.drawable.scanandpay,
            R.drawable.termdeposit,
            R.drawable.addbeneficiary,
            R.drawable.transactionhistory,
            R.drawable.cardmanagement,
            R.drawable.requests,
            R.drawable.standingorder,
            R.drawable.settings,
            R.drawable.e_comm,
            R.drawable.fcyexchange,
        )
        for (i in textArray.indices) {
            menuItems.add(MainMenuAdapter.MainMenuItem(i, resourcesArray[i], textArray[i]))
        }
        return menuItems
    }

    fun getProfile(): LiveData<UserModel> {
        val liveData = LiveEvent<UserModel>()
        safeLauncher {
            liveData.value = userRepository.getCurrentUserModel()
        }
        return liveData
    }

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

    fun updateCurrentRetailerModel() = safeLauncher {
        val userId = userRepository.getCurrentUserModel()?.id
        if (userId.isNullOrEmpty()) {
            logout()
            return@safeLauncher
        }
        val result = userRepository.getUserById(userId)
        if (result is StatefulResult.Success && result.data != null)
            userRepository.setCurrentUserModel(result.data)
        else
            logout()
    }

    fun logout() = safeLauncher {
        userRepository.removeAllPref()
        _onLogOutLiveData.value = true
    }
}