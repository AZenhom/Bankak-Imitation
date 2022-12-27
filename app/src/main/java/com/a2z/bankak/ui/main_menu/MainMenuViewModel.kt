package com.a2z.bankak.ui.main_menu

import android.content.Context
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
) : BaseViewModel() {

    fun getMenuItem(context: Context): List<MainMenuAdapter.MainMenuItem> {
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
}