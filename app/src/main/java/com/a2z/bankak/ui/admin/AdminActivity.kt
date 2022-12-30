package com.a2z.bankak.ui.admin

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.databinding.ActivityNewAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : BaseActivity<ActivityNewAdminBinding, AdminViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, AdminActivity::class.java)
    }

    override val viewModel: AdminViewModel by viewModels()
    override val binding by viewBinding(ActivityNewAdminBinding::inflate)

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() = with(binding) {
        registerBtn.setOnClickListener {
            val id = etId.text.toString().trim()
            val idFull = etIdFull.text.toString().trim()
            val name = etName.text.toString().trim()
            val branch = etBranch.text.toString().trim()
            val credit = try {
                etCredit.text.toString().trim().toInt()
            } catch (e: Exception) {
                0
            }
            val password = edmbPwd.text.toString().trim()
            if (id.isEmpty() ||
                idFull.isEmpty() ||
                name.isEmpty() ||
                branch.isEmpty() ||
                password.isEmpty()
            ) {
                showWarningMsg(getString(R.string.fieldsEmp_Err))
                return@setOnClickListener
            }
            viewModel.register(id, idFull, name, branch, credit, password)
                .observe(this@AdminActivity) {
                    if (it) {
                        showSuccessMsg("Account Created Successfully")
                    } else {
                        showErrorMsg(getString(R.string.invalid_id_password))
                    }
                    finish()
                }
        }
    }
}