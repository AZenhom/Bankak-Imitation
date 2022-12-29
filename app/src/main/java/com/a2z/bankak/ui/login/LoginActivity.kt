package com.a2z.bankak.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewLoginBinding
import com.a2z.bankak.ui.main_menu.MainMenuActivity
import com.a2z.bankak.ui.splash.SplashActivity
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
        switchLang.setOnClickListener { switchLanguage() }
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

    private fun startLogin(id: String, password: String) =
        viewModel.login(id, password).observe(this) {
            if (it) startMainMenuActivity()
            else {
                showErrorMsg(getString(R.string.invalid_id_password))
            }
        }

    private fun startMainMenuActivity() {
        startActivity(MainMenuActivity.getIntent(this))
        finish()
    }

    private fun switchLanguage() {
        viewModel.switchLanguage(this).observe(this) {
            val intent = SplashActivity.getIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}