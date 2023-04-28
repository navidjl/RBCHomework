package com.example.myapplication.model

import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountType

sealed class AccountRowItem
data class AccountDataRowItem(val account: Account) : AccountRowItem()
data class AccountHeaderRowItem(val productType: AccountType) : AccountRowItem()
