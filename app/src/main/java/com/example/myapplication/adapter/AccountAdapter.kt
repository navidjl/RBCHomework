package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.AccountLayoutItemBinding
import com.example.myapplication.databinding.HeaderLayoutItemBinding
import com.example.myapplication.model.AccountDataRowItem
import com.example.myapplication.model.AccountHeaderRowItem
import com.example.myapplication.model.AccountRowItem
import com.example.myapplication.util.OnItemClickListener
import com.example.myapplication.viewholder.AccountViewHolder
import com.example.myapplication.viewholder.BaseViewHolder
import com.example.myapplication.viewholder.HeaderViewHolder

class AccountAdapter(
    private val accountList: List<AccountRowItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var headerBinding: HeaderLayoutItemBinding
    private lateinit var accountBinding: AccountLayoutItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            ViewType.Header.ordinal -> {
                headerBinding = HeaderLayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HeaderViewHolder(headerBinding)
            }
            ViewType.Content.ordinal -> {
                accountBinding = AccountLayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                AccountViewHolder(accountBinding)
            }
            else -> throw IllegalArgumentException("Not a layout")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is AccountViewHolder -> {
                val item = accountList[position] as AccountDataRowItem
                with(item.account) {
                    holder.binding.displayName.text = name
                    holder.binding.accountNumber.text = number
                    holder.binding.balance.text = holder.binding.root.context.getString(
                        R.string.amount_currency,
                        balance
                    )

                    holder.binding.accountConstraintView.setOnClickListener {
                        onItemClickListener.onItemClick(item)
                    }
                }
            }

            is HeaderViewHolder -> {
                val item = accountList[position] as AccountHeaderRowItem
                holder.binding.accountTypeHeader.text = item.productType.name
            }
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (accountList[position]) {
            is AccountHeaderRowItem -> ViewType.Header.ordinal
            else -> ViewType.Content.ordinal
        }
    }
}