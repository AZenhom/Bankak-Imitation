package com.a2z.bankak.ui.history.history_list

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.a2z.bankak.data.repository.TransactionsRepository
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    fun getTransactions(): LiveData<List<TransactionModel>> {
        showLoading()
        val liveData = LiveEvent<List<TransactionModel>>()
        safeLauncher {
            val result =
                transactionsRepository.getUserTransactions(userRepository.getUserId() ?: "")
            hideLoading()
            if (result is StatefulResult.Success)
                liveData.value = result.data ?: listOf()
            else
                handleError(result.errorModel)
        }
        return liveData
    }
}
