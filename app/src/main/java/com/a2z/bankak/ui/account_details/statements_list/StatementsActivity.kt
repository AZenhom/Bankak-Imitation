package com.a2z.bankak.ui.account_details.statements_list

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.a2z.bankak.R
import com.a2z.bankak.core.base.BaseActivity
import com.a2z.bankak.core.utils.getDateText
import com.a2z.bankak.databinding.ActivityNewStatementsBinding
import com.safetysource.core.ui.makeGone
import com.safetysource.core.ui.makeVisible
import com.safetysource.core.ui.setIsVisible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class StatementsActivity : BaseActivity<ActivityNewStatementsBinding, StatementsViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, StatementsActivity::class.java)

        const val weekInMillis = (7L * 24L * 60L * 60L * 1000L)
        const val monthInMillis = (30L * 24L * 60L * 60L * 1000L)
        const val threeMonthInMillis = (3L * 30L * 24L * 60L * 60L * 1000L)
    }

    override val viewModel: StatementsViewModel by viewModels()
    override val binding by viewBinding(ActivityNewStatementsBinding::inflate)

    private lateinit var adapter: StatementAdapter

    private var currentTab: ViewTabs? = null

    private var byDateFrom: Date? = null
    private var byDateTo: Date? = null

    override fun onActivityCreated() {
        initUI()
        selectTab(ViewTabs.TAB_LAST_FIVE)
    }

    private fun initUI() {
        with(binding.header) {
            toolbar.out.makeGone()
            toolbar.menuIcon.makeVisible()
            headerTitleLay.makeVisible()
            servTitle.text = getString(R.string.viewStmt)
            registerViewOnBackPressed(backmen)
        }
        with(binding.tabsLayout) {
            rlLastFive.setOnClickListener { selectTab(ViewTabs.TAB_LAST_FIVE) }
            rlLastSeven.setOnClickListener { selectTab(ViewTabs.TAB_LAST_SEVEN) }
            rlLastThirty.setOnClickListener { selectTab(ViewTabs.TAB_LAST_THIRTY) }
            rlLastThree.setOnClickListener { selectTab(ViewTabs.TAB_LAST_THREE) }
            rlByDate.setOnClickListener { selectTab(ViewTabs.TAB_BY_DATE) }
        }
        with(binding.selectDateLayout) {
            smtDownlay.setOnClickListener {
                if (byDateFrom == null || byDateTo == null) {
                    showWarningMsg(getString(R.string.datefieldEmpty_Err))
                    return@setOnClickListener
                }
                getTransactions(dateFrom = byDateFrom, dateTo = byDateTo)
            }
            vDateFrom.setOnClickListener {
                with(Calendar.getInstance()) {
                    byDateFrom?.let { time = it }
                    DatePickerDialog(
                        this@StatementsActivity,
                        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                            set(Calendar.YEAR, selectedYear)
                            set(Calendar.MONTH, selectedMonth)
                            set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                            edfDate.setText(time.time.getDateText("dd-MMM-yyyy"))
                            byDateFrom = time
                        }, get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
            vDateTo.setOnClickListener {
                with(Calendar.getInstance()) {
                    byDateTo?.let { time = it }
                    DatePickerDialog(
                        this@StatementsActivity,
                        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                            set(Calendar.YEAR, selectedYear)
                            set(Calendar.MONTH, selectedMonth)
                            set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 999)
                            edtDate.setText(time.time.getDateText("dd-MMM-yyyy"))
                            byDateTo = time
                        }, get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }

        adapter = StatementAdapter()
        binding.rvTransferMenu.adapter = adapter
    }

    private fun selectTab(tab: ViewTabs) = with(binding.tabsLayout) {
        if (tab == currentTab) return@with
        currentTab = tab
        adapter.submitList(emptyList())

        // Last Five
        cvLastFive.setIsVisible(tab == ViewTabs.TAB_LAST_FIVE)
        vvLastFive.setIsVisible(tab != ViewTabs.TAB_LAST_FIVE)
        vhLastFive.setIsVisible(tab == ViewTabs.TAB_LAST_FIVE)

        // Last Seven
        cvLastSeven.setIsVisible(tab == ViewTabs.TAB_LAST_SEVEN)
        vvLastSeven.setIsVisible(tab != ViewTabs.TAB_LAST_SEVEN)
        vhLastSeven.setIsVisible(tab == ViewTabs.TAB_LAST_SEVEN)

        // Last THIRTY
        cvLastThirty.setIsVisible(tab == ViewTabs.TAB_LAST_THIRTY)
        vvLastThirty.setIsVisible(tab != ViewTabs.TAB_LAST_THIRTY)
        vhLastThirty.setIsVisible(tab == ViewTabs.TAB_LAST_THIRTY)

        // Last Three
        cvLastThree.setIsVisible(tab == ViewTabs.TAB_LAST_THREE)
        vvLastThree.setIsVisible(tab != ViewTabs.TAB_LAST_THREE)
        vhLastThree.setIsVisible(tab == ViewTabs.TAB_LAST_THREE)

        // By Date
        cvByDate.setIsVisible(tab == ViewTabs.TAB_BY_DATE)
        vhByDate.setIsVisible(tab == ViewTabs.TAB_BY_DATE)
        binding.selectDateLayout.stmtByDateLay.setIsVisible(tab == ViewTabs.TAB_BY_DATE)

        when (tab) {
            ViewTabs.TAB_LAST_FIVE -> getTransactions(limit5 = true)
            ViewTabs.TAB_LAST_SEVEN -> getTransactions(dateFrom = Date(System.currentTimeMillis() - weekInMillis))
            ViewTabs.TAB_LAST_THIRTY -> getTransactions(dateFrom = Date(System.currentTimeMillis() - monthInMillis))
            ViewTabs.TAB_LAST_THREE -> getTransactions(dateFrom = Date(System.currentTimeMillis() - threeMonthInMillis))
            ViewTabs.TAB_BY_DATE -> Unit
        }
    }

    private fun getTransactions(
        dateFrom: Date? = null,
        dateTo: Date? = null,
        limit5: Boolean = false
    ) = viewModel.getTransactions(dateFrom, dateTo, limit5).observe(this) {
        adapter.submitList(it)
    }

    private enum class ViewTabs {
        TAB_LAST_FIVE,
        TAB_LAST_SEVEN,
        TAB_LAST_THIRTY,
        TAB_LAST_THREE,
        TAB_BY_DATE
    }
}