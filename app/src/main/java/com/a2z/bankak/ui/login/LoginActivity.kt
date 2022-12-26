package com.a2z.bankak.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityNewLoginBinding, LoginViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override val viewModel: LoginViewModel by viewModels()
    override val binding by viewBinding(ActivityNewLoginBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() = with(binding) {
        loginBtn.setOnClickListener {
            val id = edtmbCif.text.toString().trim()
            val password = edmbPwd.text.toString().trim()
            if (id.isEmpty() || password.isEmpty()) {
                showWarningMsg(getString(R.string.fieldsEmp_Err))
                return@setOnClickListener
            }
            startLogin(id, password)
        }
    }

    private fun startLogin(id: String, password: String) {

    }
}