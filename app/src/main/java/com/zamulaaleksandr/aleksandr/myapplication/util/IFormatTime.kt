package com.zamulaaleksandr.aleksandr.myapplication.util

interface IFormatTime {
    fun formatDate(ts: Long): String
    fun formatTime(ts: Long): String
    fun formatDateAndTime(ts: Long): String = "${formatDate(ts)} ${formatTime(ts)}"
}