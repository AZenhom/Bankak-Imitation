package com.a2z.bankak.ui.account_details.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.core.utils.formatNumber
import com.a2z.bankak.databinding.ActivityNewAccountDetailsBinding
import com.a2z.bankak.ui.account_details.statements_list.StatementsActivity
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsActivity :
    BaseActivity<ActivityNewAccountDetailsBinding, AccountDetailsViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, AccountDetailsActivity::class.java)
    }

    override val viewModel: AccountDetailsViewModel by viewModels()
    override val binding by viewBinding(ActivityNewAccountDetailsBinding::inflate)

    override fun onActivityCreated() {
        initUI()
        getProfile()
    }

    private fun initUI() {
        // Binding to Views
        with(binding.header) {
            toolbar.out.makeGone()
            toolbar.info.makeVisible()
            toolbar.menuIcon.makeVisible()
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.accSumTitle)
            registerViewOnBackPressed(backmen)
        }
        binding.summary.buttons.viewStmtLay.setOnClickListener { openStatementsActivity() }
    }

    @SuppressLint("SetTextI18n")
    private fun getProfile() {
        viewModel.getProfile().observe(this) {
            with(binding.summary) {
                accNo.text = "${getString(R.string.account_no)} ${it.idFull}"
                accBal.text = it.credit?.formatNumber()
            }
        }
    }

    private fun openStatementsActivity() {
        startActivity(StatementsActivity.getIntent(this))
    }
}