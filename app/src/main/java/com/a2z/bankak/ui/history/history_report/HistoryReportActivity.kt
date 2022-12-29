package com.a2z.bankak.ui.history.history_report

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableRow
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.databinding.ActivityNewHistoryReportBinding
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryReportActivity :
    BaseActivity<ActivityNewHistoryReportBinding, HistoryReportViewModel>() {

    companion object {
        const val TRANSACTION = "TRANSACTION"
        fun getIntent(context: Context, transaction: TransactionModel) =
            Intent(context, HistoryReportActivity::class.java).apply {
                putExtra(TRANSACTION, transaction)
            }
    }

    override val viewModel: HistoryReportViewModel by viewModels()
    override val binding by viewBinding(ActivityNewHistoryReportBinding::inflate)

    private lateinit var locale: String

    override fun onActivityCreated() {
        initUI()
    }

    private fun initUI() {
        // Binding to Views
        with(binding.header) {
            toolbar.out.makeGone()
            toolbar.menuIcon.makeVisible()
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.payTrxDetailsTitle)
            registerViewOnBackPressed(backmen)
        }
        with(binding.footer){
            sucessresOkbtn.makeGone()
        }
        viewModel.getLanguage().observe(this) {
            locale = it
            getTableRows()
        }
    }

    private fun getTableRows() = viewModel.getTransactionTable(this).forEach {
        binding.resultTablay.addView(createTableRow(it.first, it.second))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createTableRow(tv1Text: String, tv2Text: String) = TableRow(this).apply {
        layoutParams =
            TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        val margin = resources.getDimension(R.dimen._4dp).toInt()
        background = resources.getDrawable(R.drawable.reciept_row_graybg, null)
        weightSum = 1F
        val tv1 = AppCompatTextView(this@HistoryReportActivity).apply {
            text = tv1Text
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(resources.getColor(R.color.textClr, null))
            layoutParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5F).apply {
                topMargin = margin
                leftMargin = margin
                rightMargin = margin
                bottomMargin = margin
            }
        }
        val tv2 = AppCompatTextView(this@HistoryReportActivity).apply {
            text = tv2Text
            setTextColor(resources.getColor(R.color.textClr, null))
            layoutParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5F).apply {
                topMargin = margin
                leftMargin = margin
                rightMargin = margin
                bottomMargin = margin
            }
        }
        if (locale == "ar") {
            tv1.gravity = Gravity.RIGHT
            tv2.gravity = Gravity.LEFT
            addView(tv2)
            addView(tv1)
        } else {
            tv1.gravity = Gravity.LEFT
            tv2.gravity = Gravity.RIGHT
            addView(tv1)
            addView(tv2)
        }
    }
}