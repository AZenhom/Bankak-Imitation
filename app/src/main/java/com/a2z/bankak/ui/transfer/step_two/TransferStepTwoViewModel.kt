package com.a2z.bankak.ui.transfer.step_two

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.cache.SettingsDataStore
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.ui.transfer.transfer_menu.TransferMenuAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferStepTwoViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val account: UserModel? = savedStateHandle[TransferStepTwoActivity.ACCOUNT]

    fun getAccountTable(context: Context): List<Pair<String, String>> {
        val titles = mutableListOf<Pair<String, String>>()
        val textArray = context.resources.getStringArray(R.array.account_params)
        titles.add(Pair(textArray[0], account?.idFull ?: ""))
        titles.add(Pair(textArray[1], account?.name ?: ""))
        titles.add(Pair(textArray[2], account?.type ?: ""))
        titles.add(Pair(textArray[3], account?.branch ?: ""))
        return titles
    }

    fun getLanguage(): LiveData<String> {
        val liveEvent = MutableLiveData<String>()
        safeLauncher {
            liveEvent.value = settingsDataStore.getLanguage()
        }
        return liveEvent
    }
}