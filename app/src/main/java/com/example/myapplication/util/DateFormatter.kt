package com.example.myapplication.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(date: Calendar): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).format(date.time)
