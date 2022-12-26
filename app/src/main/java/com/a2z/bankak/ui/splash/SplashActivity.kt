package com.a2z.bankak.ui.splash

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.NewLayoutSplashActivityBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<NewLayoutSplashActivityBinding, SplashViewModel>() {

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

    private fun checkNavigation(){
        showSuccessMsg("GIF ENDED")
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