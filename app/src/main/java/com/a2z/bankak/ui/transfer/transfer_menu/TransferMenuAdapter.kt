package com.a2z.bankak.ui.transfer.transfer_menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a2z.bankak.databinding.NewItemTransferMenuBinding

class TransferMenuAdapter constructor(
    private val onItemClicked: ((item: TransferMenuItem) -> Unit)? = null,
) : ListAdapter<TransferMenuAdapter.TransferMenuItem, TransferMenuAdapter.ItemViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            NewItemTransferMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: NewItemTransferMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TransferMenuItem) {
            with(binding) {
                // Item Data
                tvText.text = item.text
                ivIcon.setImageResource(item.imgResId)

                // Click Listeners
                cvRootView.setOnClickListener {
                    onItemClicked?.invoke(item)
                }

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransferMenuItem>() {
            override fun areItemsTheSame(
                oldItem: TransferMenuItem,
                newItem: TransferMenuItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TransferMenuItem,
                newItem: TransferMenuItem
            ): Boolean = oldItem.id == newItem.id &&
                    oldItem.imgResId == newItem.imgResId &&
                    oldItem.text == newItem.text
        }
    }

    data class TransferMenuItem(
        val id: Int,
        val imgResId: Int,
        val text: String
    )

}
