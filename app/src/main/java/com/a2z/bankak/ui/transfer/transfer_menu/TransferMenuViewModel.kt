package com.a2z.bankak.ui.transfer.transfer_menu

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

    fun getAccount(): LiveData<UserModel> {
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