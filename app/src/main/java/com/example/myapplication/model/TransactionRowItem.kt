package com.example.myapplication.model

import com.rbc.rbcaccountlibrary.Transaction

data class TransactionRowItem(val account: AccountInformation, val transaction: Transaction)