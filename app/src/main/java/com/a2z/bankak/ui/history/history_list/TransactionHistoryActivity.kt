package com.a2z.bankak.ui.history.history_list

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.databinding.ActivityNewTransactionHistoryBinding
import com.a2z.bankak.ui.history.history_report.HistoryReportActivity
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryActivity :
    BaseActivity<ActivityNewTransactionHistoryBinding, TransactionHistoryViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, TransactionHistoryActivity::class.java)
    }

    override val viewModel: TransactionHistoryViewModel by viewModels()
    override val binding by viewBinding(ActivityNewTransactionHistoryBinding::inflate)

    private lateinit var adapter: TransactionHistoryAdapter

    override fun onActivityCreated() {
        initUI()
        getTransactions()
    }

    private fun initUI() {
        with(binding.header) {
            toolbar.out.makeGone()
            toolbar.menuIcon.makeVisible()
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.trxHist)
            registerViewOnBackPressed(backmen)
        }

        adapter = TransactionHistoryAdapter { openTransactionReportActivity(it) }
        binding.rvTransferMenu.adapter = adapter

    }

    private fun getTransactions() = viewModel.getTransactions().observe(this) {
        adapter.submitList(it)
    }

    private fun openTransactionReportActivity(transaction: TransactionModel) {
        startActivity(HistoryReportActivity.getIntent(this, transaction))
    }
}