package com.a2z.bankak.ui.transfer.step_one

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.databinding.ActivityNewTransferStepOneBinding
import com.a2z.bankak.ui.transfer.step_two.TransferStepTwoActivity
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransferStepOneActivity :
    BaseActivity<ActivityNewTransferStepOneBinding, TransferStepOneViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, TransferStepOneActivity::class.java)
    }

    override val viewModel: TransferStepOneViewModel by viewModels()
    override val binding by viewBinding(ActivityNewTransferStepOneBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() {
        with(binding.header) {
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.othFtTitle)
            registerViewOnBackPressed(backmen)
        }
        with(binding.tabsLayout) {
            tabTwo.makeGone()
            tabOne.text = getString(R.string.payDirecTab)
        }
        with(binding.searchLayout) {
            accOrCifChkLay.makeVisible()
            checkBtn.makeVisible()
            checkBtn.setOnClickListener {
                val accNo = edtAccNoOrCif.text.toString().trim()
                if (accNo.isNotEmpty()) getAccount(accNo)
            }
        }
    }

    private fun getAccount(accNo: String) {
        viewModel.getAccount(accNo).observe(this) {
            if (it != null) openTransferStepTwoActivity(it)
        }
    }

    private fun openTransferStepTwoActivity(account: UserModel) {
        startActivity(TransferStepTwoActivity.getIntent(this, account))
    }
}