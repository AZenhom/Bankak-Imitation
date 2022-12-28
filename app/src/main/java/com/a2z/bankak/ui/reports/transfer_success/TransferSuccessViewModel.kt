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
        val notAvailable = context.getString(R.string.not_available)
        val titles = mutableListOf<Pair<String, String>>()
        val textArray = context.resources.getStringArray(R.array.report_params)
        titles.add(Pair(textArray[0], account?.id ?: notAvailable))
        titles.add(
            Pair(
                textArray[1],
                account?.createdAt?.time?.getDateText("dd-MMM-yyyy HH:mm:ss") ?: notAvailable
            )
        )
        titles.add(Pair(textArray[2], account?.fromId ?: notAvailable))
        titles.add(Pair(textArray[3], account?.toId ?: notAvailable))
        titles.add(Pair(textArray[4], account?.toName ?: notAvailable))
        titles.add(Pair(textArray[5], account?.toMobile ?: notAvailable))
        titles.add(Pair(textArray[6], account?.comment ?: notAvailable))
        titles.add(Pair(textArray[7], account?.amount?.toString() ?: notAvailable))
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