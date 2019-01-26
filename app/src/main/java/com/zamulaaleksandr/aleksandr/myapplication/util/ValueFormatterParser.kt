package com.zamulaaleksandr.aleksandr.myapplication.util

import java.util.*


import android.content.Context
import android.text.format.DateFormat


open class ValueFormatterParser(val context: Context): IFormatTime {

    private val dateFormatter = DateFormat.getDateFormat(context)
    private val timeFormatter = DateFormat.getTimeFormat(context)


    override fun formatDate(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; dateFormatter.format(time) }
    override fun formatTime(ts: Long): String = Calendar.getInstance().run { timeInMillis = ts; timeFormatter.format(time) }
}