package com.a2z.bankak.ui.transfer.step_two

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.TableRow
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.data.model.UserModel
import com.a2z.bankak.databinding.ActivityNewTransferStepTwoBinding
import com.a2z.bankak.ui.reports.transfer_failure.TransferFailureActivity
import com.a2z.bankak.ui.reports.transfer_success.TransferSuccessActivity
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferStepTwoActivity :
    BaseActivity<ActivityNewTransferStepTwoBinding, TransferStepTwoViewModel>() {

    companion object {
        const val ACCOUNT = "ACCOUNT"
        fun getIntent(context: Context, account: UserModel) =
            Intent(context, TransferStepTwoActivity::class.java).apply {
                putExtra(ACCOUNT, account)
            }
    }

    override val viewModel: TransferStepTwoViewModel by viewModels()
    override val binding by viewBinding(ActivityNewTransferStepTwoBinding::inflate)

    private lateinit var locale: String

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() {
        with(binding.header) {
            toolbar.out.makeGone()
            toolbar.menuIcon.makeVisible()
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.othFtTitle)
            registerViewOnBackPressed(backmen)
        }
        with(binding.tabsLayout) {
            tabTwo.makeGone()
            tabOne.text = getString(R.string.payDirecTab)
        }
        with(binding.tableLayout) {
            edtAccSpinner.setText(viewModel.account?.idFull.toString())
        }
        with(binding.tableLayout.btnsLayout) {
            registerViewOnBackPressed(btnCancel)
            btnSubmit.setOnClickListener { createTransaction() }
        }
        viewModel.getLanguage().observe(this) {
            locale = it
            getTableRows()
        }
    }

    private fun getTableRows() = viewModel.getAccountTable(this).forEach {
        binding.tableLayout.othFtConfiTablay.addView(createTableRow(it.first, it.second))
    }

    private fun createTableRow(tv1Text: String, tv2Text: String) = TableRow(this).apply {
        layoutParams =
            TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        weightSum = 1F
        val tv1 = AppCompatTextView(this@TransferStepTwoActivity).apply {
            text = tv1Text
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(resources.getColor(R.color.black, null))
            layoutParams = TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.38F).apply {
                topMargin = resources.getDimension(R.dimen._20dp).toInt()
            }
        }
        val tv2 = AppCompatTextView(this@TransferStepTwoActivity).apply {
            text = tv2Text
            setTextColor(resources.getColor(R.color.black, null))
            layoutParams = TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.62F).apply {
                topMargin = resources.getDimension(R.dimen._20dp).toInt()
            }
        }
        if (locale == "ar") {
            tv2.gravity = Gravity.RIGHT
            addView(tv2)
            addView(tv1)
        } else {
            tv2.gravity = Gravity.LEFT
            addView(tv1)
            addView(tv2)
        }
    }

    private fun createTransaction() = with(binding.tableLayout) {
        val amountText = edtOthftAmt.text.toString().trim()
        if (amountText.isEmpty()) {
            showWarningMsg(getString(R.string.fieldEmpAmount_Err))
            return@with
        }
        val comment = edtRemarks.text.toString().trim()
        viewModel.createTransaction(amountText.toInt(), comment.ifEmpty { null })
            .observe(this@TransferStepTwoActivity) {
                if (it != null) openTransactionSuccessActivity(it)
                else openTransactionFailureActivity()
            }
    }

    private fun openTransactionSuccessActivity(transaction: TransactionModel) {
        startActivity(TransferSuccessActivity.getIntent(this, transaction))
        finish()
    }

    private fun openTransactionFailureActivity() {
        startActivity(TransferFailureActivity.getIntent(this))
        finish()
    }
}