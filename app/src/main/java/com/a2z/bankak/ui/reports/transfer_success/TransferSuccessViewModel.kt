package com.a2z.bankak.ui.reports.transfer_success

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.core.utils.getDateText
import com.a2z.bankak.data.cache.SettingsDataStore
import com.a2z.bankak.data.model.TransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TransferSuccessViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val account: TransactionModel? = savedStateHandle[TransferSuccessActivity.TRANSACTION]

    fun getTransactionTable(context: Context): List<Pair<String, String>> {
        val NA = context.getString(R.string.not_available)
        val titles = mutableListOf<Pair<String, String>>()
        val textArray = context.resources.getStringArray(R.array.report_params)
        titles.add(Pair(textArray[0], account?.id ?: NA))
        titles.add(Pair(textArray[1], account?.createdAt?.time?.getDateText("dd-MMM-yyyy hh:mm:ss") ?: NA))
        titles.add(Pair(textArray[2], account?.fromId ?: NA))
        titles.add(Pair(textArray[3], account?.toId ?: NA))
        titles.add(Pair(textArray[4], account?.toName ?: NA))
        titles.add(Pair(textArray[5], account?.toMobile ?: NA))
        titles.add(Pair(textArray[6], account?.comment ?: NA))
        titles.add(Pair(textArray[7], account?.amount?.toString() ?: NA))
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