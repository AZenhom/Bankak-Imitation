package com.a2z.bankak.ui.reports.transfer_failure

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewLoginBinding
import com.a2z.bankak.databinding.ActivityNewTransferFailureBinding
import com.a2z.bankak.ui.login.LoginActivity
import com.a2z.bankak.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFailureActivity :
    BaseActivity<ActivityNewTransferFailureBinding, TransferFailureViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, TransferFailureActivity::class.java)
    }

    override val viewModel: TransferFailureViewModel by viewModels()
    override val binding by viewBinding(ActivityNewTransferFailureBinding::inflate)

    override fun onActivityCreated() {
        registerViewOnBackPressed(binding.failresOkbtn)
    }
}