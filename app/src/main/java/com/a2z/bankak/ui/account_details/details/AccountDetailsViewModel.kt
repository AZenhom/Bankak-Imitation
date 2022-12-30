package com.a2z.bankak.ui.account_details.details

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    fun getProfile(): LiveData<UserModel> {
        val liveData = LiveEvent<UserModel>()
        safeLauncher {
            liveData.value = userRepository.getCurrentUserModel()
        }
        return liveData
    }
}