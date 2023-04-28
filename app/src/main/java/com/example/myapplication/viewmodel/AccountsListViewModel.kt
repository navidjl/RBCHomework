package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.AccountDataRowItem
import com.example.myapplication.model.AccountHeaderRowItem
import com.example.myapplication.model.AccountRowItem
import com.example.myapplication.state.AccountListState
import com.rbc.rbcaccountlibrary.AccountProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsListViewModel @Inject constructor(private val accountProvider: AccountProvider) :
    ViewModel() {

    val accountListState = MutableLiveData<AccountListState>(AccountListState.Loading)

    fun getAccounts() {
        try {
            viewModelScope.launch {
                accountListState.postValue(AccountListState.Success(getAccountList()))
            }
        } catch (e: Exception) {
            accountListState.postValue(AccountListState.Error)
        }
    }

    private fun getAccountList(): List<AccountRowItem> =
        accountProvider.getAccountsList().sortedBy { account ->
            account.type
        }.let { accountList ->
            accountList.groupBy { it.type }
                .flatMap { (accountType, accounts) ->
                    listOf<AccountRowItem>(
                        AccountHeaderRowItem(accountType)
                    ) + accounts.map {
                        AccountDataRowItem(
                            it
                        )
                    }
                }
        }
}