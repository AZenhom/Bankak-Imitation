package com.a2z.bankak.ui.transfer.step_two

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.cache.SettingsDataStore
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.a2z.bankak.data.repository.TransactionsRepository
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TransferStepTwoViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository,
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

    fun createTransaction(amount: Int, comment: String?): LiveData<TransactionModel?> {
        val liveEvent = LiveEvent<TransactionModel?>()
        safeLauncher {
            showLoading()
            val transaction = TransactionModel(
                id = "2000${Random.nextLong(1000000, 9999999)}".toLong().toString(),
                fromId = userRepository.getUserId(),
                toId = account?.idFull,
                toName = account?.name,
                comment = comment,
                amount = amount,
                createdAt = Calendar.getInstance().time
            )
            val userModel = userRepository.getUserByIdFull(userRepository.getUserId() ?: "").data
            if (userModel == null || (userModel.credit ?: 0) < amount) {
                hideLoading()
                liveEvent.value = null
                return@safeLauncher
            }
            userModel.apply { credit = credit!! - amount }
            account!!.apply { credit = (credit ?: 0) + amount }
            userRepository.setCurrentUserModel(userModel)
            val result1 = userRepository.createUpdateUser(userModel)
            val result2 = userRepository.createUpdateUser(account)
            val result3 = transactionsRepository.createTransactions(transaction)
            if (result1 is StatefulResult.Success && result2 is StatefulResult.Success && result3 is StatefulResult.Success)
                liveEvent.value = transaction
            else
                liveEvent.value = null
        }
        return liveEvent
    }
}