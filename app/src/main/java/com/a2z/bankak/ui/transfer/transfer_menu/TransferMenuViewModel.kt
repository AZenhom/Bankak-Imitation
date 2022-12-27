package com.a2z.bankak.ui.transfer.transfer_menu

import android.content.Context
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TransferMenuViewModel @Inject constructor(
) : BaseViewModel() {

    fun getMenuItems(context: Context): List<TransferMenuAdapter.TransferMenuItem> {
        val menuItems = mutableListOf<TransferMenuAdapter.TransferMenuItem>()
        val textArray = context.resources.getStringArray(R.array.ft_menus).toMutableList()
        textArray.removeAt(0)
        val resourcesArray = listOf(
            R.drawable.ftothacc,
            R.drawable.mywalletsmbnoicon,
            R.drawable.cardsupplementary,
        )
        for (i in textArray.indices) {
            menuItems.add(TransferMenuAdapter.TransferMenuItem(i, resourcesArray[i], textArray[i]))
        }
        return menuItems
    }
}