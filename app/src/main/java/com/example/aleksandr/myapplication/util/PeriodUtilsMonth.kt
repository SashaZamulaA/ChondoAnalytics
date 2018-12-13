package com.example.aleksandr.myapplication.util

import android.content.Context
import android.text.format.DateUtils
import com.example.aleksandr.myapplication.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object PeriodUtilsMonth {

    val DIVIDER_HOUR = "hour"
    val DIVIDER_DAY = "day"
    val DIVIDER_WEEK = "week"
    val DIVIDER_MONTH = "month"
    val DIVIDER_QUARTER = "quarter"//seems to not be used
    val DIVIDER_YEAR = "year"
    val DIVIDER_CUSTOM = "custom"

    //returns last millisecond of current day
    val endDateFromToday: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            calendar.time = getStartOfDay(calendar.time)
            return calendar.timeInMillis - 1
        }

    fun formatDatePeriod(fromDate: Long, toDate: Long, period: Int): String {
        val builder = StringBuilder()
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR).toLong()

                calendar.timeInMillis = fromDate
                val month = SimpleDateFormat("LLLL", Locale.getDefault()).format(fromDate)
                builder.append(month.substring(0, 1).toUpperCase()).append(month.substring(1))
                if (calendar.get(Calendar.YEAR).toLong() != currentYear)
                    builder.append(" ").append(calendar.get(Calendar.YEAR))
               return builder.toString()
    }

    fun isSame(unit: Int, fromDate: Long, toDate: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fromDate
        val from = calendar.get(unit)
        calendar.timeInMillis = toDate
        val to = calendar.get(unit)
        return from == to
    }

    fun formatYear(fromDate: Long, toDate: Long): String {
        val builder = StringBuilder()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fromDate
        builder.append(calendar.get(Calendar.YEAR))
        if (!isSame(Calendar.YEAR, fromDate, toDate)) {
            calendar.timeInMillis = toDate
            builder.append(" - ").append(calendar.get(Calendar.YEAR))
        }
        return builder.toString()
    }

    private fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun getEndDate(fromDate: Long, selectedPeriod: Int, customPeriodInDays: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fromDate - 1

        when (selectedPeriod) {
            Utils.DAY_PERIOD -> calendar.add(Calendar.DATE, 1)
            Utils.WEEK_PERIOD -> calendar.add(Calendar.DATE, 7)
            Utils.MONTH_PERIOD//some retarded shit right here
            -> {
                calendar.add(Calendar.DATE, 1)
                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.DATE, -1)
            }
            Utils.YEAR_PERIOD -> calendar.add(Calendar.YEAR, 1)
            Utils.CUSTOM_PERIOD -> calendar.add(Calendar.DATE, customPeriodInDays)
        }
        return if (calendar.timeInMillis > endDateFromToday)
            endDateFromToday
        else
            calendar.timeInMillis
    }

    fun getStartDate(toDate: Long, selectedPeriod: Int, customPeriodInDays: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = toDate
        when (selectedPeriod) {
            Utils.DAY_PERIOD -> {
            }
            Utils.WEEK_PERIOD -> {
                calendar.add(Calendar.DAY_OF_MONTH, -1)//workaround because Calendar.SUNDAY is considered start of the week
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            }
            Utils.MONTH_PERIOD -> calendar.set(Calendar.DAY_OF_MONTH, 1)
            Utils.YEAR_PERIOD -> calendar.set(Calendar.DAY_OF_YEAR, 1)
            Utils.CUSTOM_PERIOD -> if (customPeriodInDays > 1)
                calendar.add(Calendar.DATE, -customPeriodInDays)
        }
        calendar.time = getStartOfDay(calendar.time)
        return calendar.time.time
    }

    fun getStartDateFromToday(selectedPeriod: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.time = getStartOfDay(calendar.time)

        when (selectedPeriod) {
            Utils.DAY_PERIOD -> {
            }
            Utils.WEEK_PERIOD -> calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            Utils.MONTH_PERIOD -> calendar.set(Calendar.DAY_OF_MONTH, 1)
            Utils.YEAR_PERIOD -> calendar.set(Calendar.DAY_OF_YEAR, 1)
            else -> {
            }
        }
        return calendar.time.time
    }

    fun canIncreasePeriod(date: Long): Boolean {
        val calendar = Calendar.getInstance()
        val currentDate = calendar.timeInMillis

        calendar.timeInMillis = date
        val toDate = calendar.timeInMillis

        return toDate <= currentDate
    }

    fun getDivider(period: Int, customPeriodInDays: Int, fromDate: Long): String {
        when (period) {
            Utils.DAY_PERIOD -> return DIVIDER_HOUR
            Utils.WEEK_PERIOD -> return DIVIDER_DAY
            Utils.MONTH_PERIOD -> return DIVIDER_DAY
            Utils.YEAR_PERIOD -> return DIVIDER_MONTH
            else -> return getCustomDivider(customPeriodInDays, fromDate)
        }
    }

    private fun getCustomDivider(periodInDays: Int, fromDate: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fromDate
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

        var maxDaysAmountForDayDivider = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth + 1
        if (dayOfMonth != 1) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.add(Calendar.MONTH, 1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            maxDaysAmountForDayDivider += calendar.get(Calendar.DAY_OF_MONTH) - 1
        }

        calendar.timeInMillis = fromDate
        var maxDaysAmountForMonthDivider = calendar.getActualMaximum(Calendar.DAY_OF_YEAR) - dayOfYear + 1
        if (dayOfYear != 1) {
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            calendar.add(Calendar.YEAR, 1)
            maxDaysAmountForMonthDivider += calendar.getActualMaximum(Calendar.DAY_OF_YEAR) - 1
        }

        return if (periodInDays == 1)
            DIVIDER_HOUR
        else if (periodInDays <= maxDaysAmountForDayDivider)
            DIVIDER_DAY
        else if (periodInDays <= maxDaysAmountForMonthDivider)
            DIVIDER_MONTH
        else
            DIVIDER_YEAR
    }


    fun getLegendByDivider(context: Context, divider: String): String {
        when (divider) {
            DIVIDER_HOUR -> return context.resources.getString(R.string.hour)
            DIVIDER_DAY -> return context.resources.getString(R.string.day)
            DIVIDER_MONTH -> return context.resources.getString(R.string.month)
            DIVIDER_QUARTER -> return context.resources.getString(R.string.quarter)
            DIVIDER_YEAR -> return context.resources.getString(R.string.year)
            else -> return ""
        }
    }

    fun getCompareBy(period: Int): String {
        when (period) {
            Utils.DAY_PERIOD -> return DIVIDER_DAY
            Utils.WEEK_PERIOD -> return DIVIDER_WEEK
            Utils.MONTH_PERIOD -> return DIVIDER_MONTH
            Utils.YEAR_PERIOD -> return DIVIDER_YEAR
            else -> return DIVIDER_CUSTOM
        }
    }
}
