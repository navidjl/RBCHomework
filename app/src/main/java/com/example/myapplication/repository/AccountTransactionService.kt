package com.example.myapplication.repository

import com.example.myapplication.di.IoDispatcher
import com.example.myapplication.model.AccountInformation
import com.example.myapplication.model.TransactionRowItem
import com.example.myapplication.util.isCreditCArd
import com.rbc.rbcaccountlibrary.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getTransactionRowItems(accountData: AccountInformation): List<TransactionRowItem> =
        withContext(coroutineDispatcher) {
            (loadTransaction(accountData) + loadCreditCardTransactions(accountData).orEmpty()).map {
                TransactionRowItem(
                    accountData, it
                )
            }.let { transactions ->
                transactions.sortedByDescending { it.transaction.date }
            }
        }

    private suspend fun loadTransaction(accountData: AccountInformation): List<Transaction> =
        coroutineScope {
            async { transactionRepository.getTransactions(accountData.account.number) }
        }.await()

    private suspend fun loadCreditCardTransactions(accountData: AccountInformation): List<Transaction>? =
        coroutineScope {
            if (accountData.account.type.isCreditCArd()) {
                async { transactionRepository.getCreditTransactions(accountData.account.number) }
            } else null
        }?.await()

}
