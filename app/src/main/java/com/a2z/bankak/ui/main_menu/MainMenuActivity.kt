package com.a2z.bankak.ui.main_menu

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewMainMenuBinding
import com.a2z.bankak.ui.account_details.statements_list.StatementsActivity
import com.a2z.bankak.ui.history.history_list.TransactionHistoryActivity
import com.a2z.bankak.ui.splash.SplashActivity
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

    private var clicksToChangeLanguage = 10

    override fun onActivityCreated() {
        initObservers()
        initUI()
        getProfile()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCurrentRetailerModel()
    }

    private fun initObservers() {
        viewModel.onLogOutLiveData.observe(this) {
            val intent = SplashActivity.getIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
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
            MainMenuAdapter(displayMetrics.widthPixels) {
                when (it.id) {
                    0 -> openAccountSummaryActivity()
                    2 -> openTransferMenuActivity()
                    7 -> openTransactionHistoryActivity()
                    8 -> switchLanguage()
                    12 -> Unit
                    else -> Unit
                }
            }

        // Binding to Views
        with(binding) {
            // Recycler View
            rvMainMenu.adapter = adapter
            adapter.submitList(viewModel.getMenuItems(this@MainMenuActivity))
            // Greeting
            tvGreeting.text = greetingsText
            // Logout
            header.toolbar.out.setOnClickListener { viewModel.logout() }
        }
    }

    private fun getProfile() {
        viewModel.getProfile().observe(this) {
            binding.tvName.text = it?.name
        }
    }

    private fun switchLanguage() {
        clicksToChangeLanguage--
        if (clicksToChangeLanguage != 0)
            return
        viewModel.switchLanguage(this).observe(this) {
            val intent = SplashActivity.getIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun openAccountSummaryActivity() {
        startActivity(StatementsActivity.getIntent(this))
    }

    private fun openTransferMenuActivity() {
        startActivity(TransferMenuActivity.getIntent(this))
    }

    private fun openTransactionHistoryActivity() {
        startActivity(TransactionHistoryActivity.getIntent(this))
    }
}