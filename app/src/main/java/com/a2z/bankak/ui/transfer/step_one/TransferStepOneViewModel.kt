package com.a2z.bankak.ui.transfer.step_one

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class TransferStepOneViewModel @Inject constructor(
) : BaseViewModel() {

    fun getAccount(accNo: String): LiveData<UserModel?> {
        val liveData = LiveEvent<UserModel>()
        safeLauncher {
            delay(100)
            liveData.value = UserModel(
                id = "12345",
                idFull = accNo,
                name = "Abdulrhman Elrsheed",
                type = "Saving Account",
                branch = "Makram Branch"
            )
        }
        return liveData
    }
}