package com.a2z.bankak.ui.admin

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.data.model.response.StatefulResult
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    fun register(
        id: String,
        idFull: String,
        name: String,
        branch: String,
        credit: Int,
        password: String
    ): LiveData<Boolean> {
        val liveData = LiveEvent<Boolean>()
        val userModel = UserModel(
            id = id,
            idFull = idFull,
            name = name,
            type = "Saving Account",
            branch = branch,
            credit = credit,
            password = password,
            admin = 0
        )
        safeLauncher {
            showLoading()
            val result = userRepository.createUpdateUser(userModel)
            hideLoading()
            liveData.value = result is StatefulResult.Success
        }
        return liveData
    }
}