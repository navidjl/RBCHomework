package com.example.myapplication.state

import com.example.myapplication.model.TransactionRowItem

sealed class AccountTransactionState {
    object Loading : AccountTransactionState()
    data class Success(val transactions: List<TransactionRowItem>) : AccountTransactionState()
    object Error : AccountTransactionState()
}