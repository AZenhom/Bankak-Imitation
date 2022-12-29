package com.a2z.bankak.ui.transfer.step_one

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class TransferStepOneViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun getAccount(id: String): LiveData<UserModel?> {
        val liveData = LiveEvent<UserModel?>()
        safeLauncher {
            showLoading()
            val result = userRepository.getUserByIdFull(id)
            hideLoading()
            if (result is StatefulResult.Success && result.data != null) {
                liveData.value = result.data
            } else liveData.value = null
        }
        return liveData
    }
}