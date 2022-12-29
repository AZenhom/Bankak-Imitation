package com.a2z.bankak.ui.splash

import androidx.lifecycle.LiveData
import com.a2z.bankak.core.base.BaseViewModel
import com.a2z.bankak.data.repository.UserRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun checkNavigation(): LiveData<NavigationCases> {
        val liveData = LiveEvent<NavigationCases>()
        safeLauncher {
            val isSignedIn = userRepository.isSignedIn()
            liveData.value =
                if (isSignedIn) NavigationCases.LOGGED_IN else NavigationCases.NOT_LOGGED_IN
        }
        return liveData
    }
}

enum class NavigationCases {
    NOT_LOGGED_IN,
    LOGGED_IN,
}