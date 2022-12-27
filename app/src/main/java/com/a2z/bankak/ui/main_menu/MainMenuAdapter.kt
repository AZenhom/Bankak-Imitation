package com.a2z.bankak.ui.main_menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a2z.bankak.databinding.NewItemMainMenuBinding

class MainMenuAdapter constructor(
    private val screenWidth: Int,
    private val onItemClicked: ((transaction: MainMenuItem) -> Unit)? = null,
) : ListAdapter<MainMenuAdapter.MainMenuItem, MainMenuAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            NewItemMainMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.width = screenWidth / 3
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: NewItemMainMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MainMenuItem) {
            with(binding) {
                // Item Data
                tvText.text = item.text
                ivIcon.setImageResource(item.imgResId)

                // Click Listeners
                llRootView.setOnClickListener {
                    onItemClicked?.invoke(item)
                }

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainMenuItem>() {
            override fun areItemsTheSame(
                oldItem: MainMenuItem,
                newItem: MainMenuItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MainMenuItem,
                newItem: MainMenuItem
            ): Boolean = oldItem.id == newItem.id &&
                    oldItem.imgResId == newItem.imgResId &&
                    oldItem.text == newItem.text
        }
    }

    data class MainMenuItem(
        val id: Int,
        val imgResId: Int,
        val text: String
    )

}
