package com.a2z.bankak.ui.history.history_report

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.core.utils.formatNumber
import com.a2z.bankak.core.utils.getDateText
import com.a2z.bankak.data.cache.SettingsDataStore
import com.a2z.bankak.data.model.TransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryReportViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val transaction: TransactionModel? = savedStateHandle[HistoryReportActivity.TRANSACTION]

    fun getTransactionTable(context: Context): List<Pair<String, String>> {
        val notAvailable = context.getString(R.string.not_available)
        val titles = mutableListOf<Pair<String, String>>()
        val textArray = context.resources.getStringArray(R.array.history_report_params)
        titles.add(Pair(textArray[0], transaction?.id ?: notAvailable))
        titles.add(
            Pair(
                textArray[1],
                transaction?.createdAt?.time?.getDateText("dd-MMM-yyyy HH:mm:ss") ?: notAvailable
            )
        )
        titles.add(
            Pair(
                textArray[2],
                context.getString(R.string.fund_transferred_to_other_account)
            )
        )
        titles.add(
            Pair(
                textArray[3],
                transaction?.amount?.formatNumber()?.plus(".00") ?: notAvailable
            )
        )
        titles.add(Pair(textArray[4], transaction?.fromIdFormatted() ?: notAvailable))
        titles.add(Pair(textArray[5], transaction?.toIdFormatted() ?: notAvailable))
        titles.add(Pair(textArray[6], context.getString(R.string.sucess_digTitle)))
        titles.add(Pair(textArray[7], transaction?.toName ?: notAvailable))
        titles.add(
            Pair(textArray[8], transaction?.comment?.ifEmpty { notAvailable } ?: notAvailable))
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