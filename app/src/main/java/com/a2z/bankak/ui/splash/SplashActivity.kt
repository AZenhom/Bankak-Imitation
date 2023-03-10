package com.a2z.bankak.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.activity.viewModels
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.NewLayoutSplashActivityBinding
import com.a2z.bankak.ui.login.LoginActivity
import com.a2z.bankak.ui.main_menu.MainMenuActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<NewLayoutSplashActivityBinding, SplashViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java)
    }

    override val viewModel: SplashViewModel by viewModels()
    override val binding by viewBinding(NewLayoutSplashActivityBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        with(binding) {
            tvAppVersion.text = "V:4.42"
            Glide
                .with(this@SplashActivity)
                .asGif()
                .load(R.drawable.splashgif)
                .listener(GifRequestListener())
                .into(ivSplash)
        }
    }

    private fun checkNavigation() = viewModel.checkNavigation().observe(this) {
        when (it) {
            NavigationCases.NOT_LOGGED_IN -> startLoginActivity()
            NavigationCases.LOGGED_IN -> startMainMenuActivity()
            else -> Unit
        }
    }

    private fun startLoginActivity() {
        startActivity(LoginActivity.getIntent(this))
        finish()
    }

    private fun startMainMenuActivity() {
        startActivity(MainMenuActivity.getIntent(this))
        finish()
    }

    private inner class GifRequestListener : RequestListener<GifDrawable> {
        override fun onLoadFailed(
            e: GlideException?, m: Any?, t: Target<GifDrawable>?, r: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            resource?.let {
                it.setLoopCount(1)
                it.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        super.onAnimationEnd(drawable)
                        checkNavigation()
                    }
                })
            }
            return false
        }

    }
}