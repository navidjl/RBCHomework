package com.example.myapplication.repository

import com.rbc.rbcaccountlibrary.AccountProvider
import com.rbc.rbcaccountlibrary.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val accountProvider: AccountProvider
) {

    fun getTransactions(accountNumber: String): List<Transaction> =
        accountProvider.getTransactions(accountNumber)

    fun getCreditTransactions(accountNumber: String): List<Transaction> =
        accountProvider.getAdditionalCreditCardTransactions(accountNumber)
}