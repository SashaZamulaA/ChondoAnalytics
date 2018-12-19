package com.example.aleksandr.myapplication.util

import java.util.*

fun startOfDay(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 1)
    return calendar.time
}

fun endOfDay(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

fun getDateStartWeek(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_WEEK, calend.getActualMinimum(Calendar.DAY_OF_WEEK))
        setTimeToBeginningOfDay(calend)
        return calend.time
    }
}

fun getDateEndWeek(): Date {

    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_WEEK,
                calend.getActualMaximum(Calendar.DAY_OF_WEEK))
        setTimeToEndofDay(calend)
        return calend.time
    }
}

fun getDateStartMonth(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_MONTH,
                calend.getActualMinimum(Calendar.DAY_OF_MONTH))
        setTimeToBeginningOfDay(calend)
        return calend.time
    }
}

fun getDateEndMonth(): Date {

    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_MONTH,
                calend.getActualMaximum(Calendar.DAY_OF_MONTH))
        setTimeToEndofDay(calend)
        return calend.time
    }
}

fun getDateStartYear(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_YEAR,
                calend.getActualMinimum(Calendar.DAY_OF_YEAR))
        setTimeToBeginningOfDay(calend)
        return calend.time
    }
}

fun getDateEndYear(): Date {

    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_YEAR,
                calend.getActualMaximum(Calendar.DAY_OF_YEAR))
        setTimeToEndofDay(calend)
        return calend.time
    }
}
private fun getCalendarForNow(): Calendar {
    val calendar = GregorianCalendar.getInstance()
    calendar.time = Date()
    return calendar
}

private fun setTimeToBeginningOfDay(calendar: Calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
}

private fun setTimeToEndofDay(calendar: Calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
}
