package com.a2z.bankak.ui.main_menu

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewMainMenuBinding
import com.a2z.bankak.ui.transfer.transfer_menu.TransferMenuActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainMenuActivity : BaseActivity<ActivityNewMainMenuBinding, MainMenuViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, MainMenuActivity::class.java)
    }

    override val viewModel: MainMenuViewModel by viewModels()
    override val binding by viewBinding(ActivityNewMainMenuBinding::inflate)

    override fun onActivityCreated() {
        initUI()
        getProfile()
    }

    private fun initUI() {
        // Getting time for greetings
        val greetingsText = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> getString(R.string.gm)
            in 12..16 -> getString(R.string.gaf)
            else -> getString(R.string.ge)
        }
        // Screen Width & Adapter
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val adapter =
            MainMenuAdapter(displayMetrics.widthPixels) { if (it.id == 2) openTransferMenuActivity() }

        // Binding to Views
        with(binding) {
            // Recycler View
            rvMainMenu.adapter = adapter
            adapter.submitList(viewModel.getMenuItems(this@MainMenuActivity))
            // Greeting
            tvGreeting.text = greetingsText
        }
    }

    private fun getProfile() {
        viewModel.getProfile().observe(this) {
            binding.tvName.text = it?.name
        }
    }

    private fun openTransferMenuActivity() {
        startActivity(TransferMenuActivity.getIntent(this))
    }
}