package com.a2z.bankak.ui.transfer.transfer_menu

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewTransferMenuBinding
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
        val adapter = TransferMenuAdapter { openTransferActivity() }
        binding.rvTransferMenu.adapter = adapter
        adapter.submitList(viewModel.getMenuItems(this@TransferMenuActivity))
    }

    private fun openTransferActivity() {

    }
}