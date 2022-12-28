package com.a2z.bankak.ui.history.history_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a2z.bankak.R
import com.a2z.bankak.core.utils.getDateText
import com.a2z.bankak.data.model.TransactionModel
import com.a2z.bankak.databinding.NewItemTransactionHistoryBinding

class TransactionHistoryAdapter constructor(
    private val onItemClicked: ((item: TransactionModel) -> Unit)? = null,
) : ListAdapter<TransactionModel, TransactionHistoryAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            NewItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: NewItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TransactionModel) {
            with(binding) {
                // Item Data
                trxDate.text =
                    item.createdAt?.time?.getDateText("dd-MMM-yyyy")
                trxAmt.text =
                    "${trxAmt.context.getString(R.string.sdg)}. ${item.amount?.toString()}"
                trxDescr.text =
                    "${trxDescr.context.getString(R.string.fund_transfered_to)}\n${item.toId}"

                // Click Listeners
                llRootView.setOnClickListener {
                    onItemClicked?.invoke(item)
                }

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionModel>() {
            override fun areItemsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean = oldItem.id == newItem.id &&
                    oldItem.toId == newItem.toId &&
                    oldItem.amount == newItem.amount &&
                    oldItem.createdAt == newItem.createdAt
        }
    }

}