package com.a2z.bankak.ui.main_menu

import android.content.Context
import androidx.lifecycle.LiveData
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
) : BaseViewModel() {

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
            delay(100)
            liveData.value = UserModel(
                id = "12345",
                idFull = "00012345000",
                name = "Abdulrhman Elrsheed",
                type = "Saving Account",
                branch = "Makram Branch"
            )
        }
        return liveData
    }
}