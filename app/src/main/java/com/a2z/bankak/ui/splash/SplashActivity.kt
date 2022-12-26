package com.a2z.bankak.ui.splash

import android.annotation.SuppressLint
import android.view.WindowManager
import androidx.activity.viewModels
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.NewLayoutSplashActivityBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<NewLayoutSplashActivityBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()
    override val binding by viewBinding(NewLayoutSplashActivityBinding::inflate)

    override fun onActivityCreated() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

}