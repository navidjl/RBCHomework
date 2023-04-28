package com.example.myapplication.model

import com.rbc.rbcaccountlibrary.Account
import java.io.Serializable

data class AccountInformation(
    val account: Account
) : Serializable