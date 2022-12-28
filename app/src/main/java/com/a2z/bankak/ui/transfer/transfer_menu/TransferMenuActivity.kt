package com.a2z.bankak.ui.transfer.transfer_menu

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.databinding.ActivityNewTransferMenuBinding
import com.a2z.bankak.ui.transfer.step_two.TransferStepTwoActivity
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferMenuActivity : BaseActivity<ActivityNewTransferMenuBinding, TransferMenuViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, TransferMenuActivity::class.java)
    }

    override val viewModel: TransferMenuViewModel by viewModels()
    override val binding by viewBinding(ActivityNewTransferMenuBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() {
        // Binding to Views
        with(binding.header) {
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.ftTitle)
            registerViewOnBackPressed(backmen)
        }
        val adapter = TransferMenuAdapter { if (it.id == 0) getAccount() }
        adapter.submitList(viewModel.getMenuItems(this@TransferMenuActivity))
        with(binding) {
            rvTransferMenu.adapter = adapter
        }
    }

    private fun getAccount() = viewModel.getAccount().observe(this) { openTransferActivity(it) }

    private fun openTransferActivity(account: UserModel) {
        startActivity(TransferStepTwoActivity.getIntent(this, account))
    }
}