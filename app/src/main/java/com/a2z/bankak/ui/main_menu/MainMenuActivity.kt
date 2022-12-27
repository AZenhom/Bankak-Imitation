package com.a2z.bankak.ui.main_menu

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import androidx.activity.viewModels
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewMainMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuActivity : BaseActivity<ActivityNewMainMenuBinding, MainMenuViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, MainMenuActivity::class.java)
    }

    override val viewModel: MainMenuViewModel by viewModels()
    override val binding by viewBinding(ActivityNewMainMenuBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() {
        with(binding) {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val adapter = MainMenuAdapter(width) { showSuccessMsg(it.text) }
            rvMainMenu.adapter = adapter
            adapter.submitList(viewModel.getMenuItem(this@MainMenuActivity))
        }
    }
}