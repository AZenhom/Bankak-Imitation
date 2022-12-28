package com.a2z.bankak.ui.history.history_list

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.TransactionModel
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
) : BaseViewModel() {

    fun getTransactions(): LiveData<List<TransactionModel>> {
        val liveData = LiveEvent<List<TransactionModel>>()
        safeLauncher {
            delay(100)
            liveData.value = getFakeTransactions()
        }
        return liveData
    }

    private fun getFakeTransactions() = listOf(
        TransactionModel("1", "", "0483020012970001", "", "", "", 123, Calendar.getInstance().time),
        TransactionModel("2", "", "1153120644970001", "", "", "", 321, Calendar.getInstance().time),
        TransactionModel("3", "", "0263022090660001", "", "", "", 300, Calendar.getInstance().time),
        TransactionModel("4", "", "0173027384190001", "", "", "", 500, Calendar.getInstance().time)
    )
}
