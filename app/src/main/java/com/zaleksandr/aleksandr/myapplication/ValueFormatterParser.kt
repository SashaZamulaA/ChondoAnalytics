package com.zaleksandr.aleksandr.myapplication

interface ValueFormatterParser {

    fun formatDate(ts: Long): String
    fun formatDateFull(ts: Long): String
    fun formatTime(ts: Long): String
    fun formatDateAndTime(ts: Long): String = "${formatDate(ts)} ${formatTime(ts)}"
}