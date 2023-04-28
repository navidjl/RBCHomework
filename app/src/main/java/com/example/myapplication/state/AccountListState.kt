package com.example.myapplication.state

import com.example.myapplication.model.AccountRowItem

sealed class AccountListState {
    object Loading : AccountListState()
    data class Success(val accountTransactionRowItems: List<AccountRowItem>) : AccountListState()
    object Error : AccountListState()
}