package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.TransactionLayoutItemBinding
import com.example.myapplication.model.TransactionRowItem
import com.example.myapplication.util.dateFormatter
import com.example.myapplication.viewholder.TransactionViewHolder

class TransactionAdapter(private val mList: List<TransactionRowItem>) :
    RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionLayoutItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { TransactionViewHolder(it) }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = mList[position]
        holder.binding.description.text = item.account.account.name
        holder.binding.transactionAmount.text =
            holder.binding.root.context.getString(
                R.string.amount_currency,
                item.transaction.amount
            )
        holder.binding.transactionDate.text = dateFormatter(item.transaction.date)
    }

    override fun getItemCount() = mList.size

    override fun getItemViewType(position: Int): Int = ViewType.Content.ordinal
}
