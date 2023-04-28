package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.AccountInformation
import com.example.myapplication.repository.TransactionService
import com.example.myapplication.state.AccountTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountTransactionViewModel @Inject constructor(
    private val transactionService: TransactionService
) : ViewModel() {
    private val _stateLd = MutableLiveData<AccountTransactionState>(AccountTransactionState.Loading)
    val stateLd: LiveData<AccountTransactionState>
        get() = _stateLd

    lateinit var account: AccountInformation

    fun getTransactionsForAccount() {
        viewModelScope.launch {
            _stateLd.postValue(AccountTransactionState.Loading)
            try {
                _stateLd.postValue(
                    AccountTransactionState.Success(
                        transactionService.getTransactionRowItems(
                            account
                        )
                    )
                )
            } catch (e: Exception) {
                _stateLd.postValue(AccountTransactionState.Error)
            }
        }
    }
}