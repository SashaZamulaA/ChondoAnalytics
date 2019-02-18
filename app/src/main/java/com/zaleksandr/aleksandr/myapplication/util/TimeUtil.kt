package com.zaleksandr.aleksandr.myapplication.util

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
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

//object MondaySunday {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val today = LocalDate.now()
//
//        // Go backward to get Monday
//        var monday = today
//        while (monday.dayOfWeek != DayOfWeek.MONDAY) {
//            monday = monday.minusDays(1)
//        }
//
//        // Go forward to get Sunday
//        var sunday = today
//        while (sunday.dayOfWeek != DayOfWeek.SUNDAY) {
//            sunday = sunday.plusDays(1)
//        }
//
//        println("Today: $today")
//        println("Monday of the Week: $monday")
//        println("Sunday of the Week: $sunday")
//    }
//}
fun startOfWeek(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_WEEK, calend.getActualMinimum(Calendar.DAY_OF_WEEK))

        setTimeToBeginningOfDay(calend)

        return calend.time
    }
}

fun endOfWeek(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_WEEK,
                calend.getActualMaximum(Calendar.DAY_OF_WEEK))

        setTimeToEndofDay(calend)
        return calend.time
    }
}

private fun getCalendarForNow(): Calendar {
    val calendar = GregorianCalendar.getInstance();

    calendar.time = Date()
    return calendar
}

fun startOfMonth(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_MONTH,
                calend.getActualMinimum(Calendar.DAY_OF_MONTH))
        setTimeToBeginningOfDay(calend)
        return calend.time
    }
}

fun endOfMonth(): Date {

    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_MONTH,
                calend.getActualMaximum(Calendar.DAY_OF_MONTH))
        setTimeToEndofDay(calend)
        return calend.time
    }
}

fun startOfYear(): Date {
    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_YEAR,
                calend.getActualMinimum(Calendar.DAY_OF_YEAR))
        setTimeToBeginningOfDay(calend)
        return calend.time
    }
}

fun endOfYear(): Date {

    run {
        val calend = getCalendarForNow()
        calend.set(Calendar.DAY_OF_YEAR,
                calend.getActualMaximum(Calendar.DAY_OF_YEAR))
        setTimeToEndofDay(calend)
        return calend.time
    }
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


fun clickByFilterIndividualGoal(individualGoalCollection: CollectionReference): Task<QuerySnapshot> {

    return individualGoalCollection.whereEqualTo("currentUserId", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
        FirebaseAuth.getInstance().uid
    } else null).get()

//    return goalCollection.whereGreaterThanOrEqualTo("time", when (value) {
//        1 -> startOfDay(Date())
//        2 -> startOfWeek()
//        3 -> startOfMonth()
//        4 -> startOfYear()
//        else -> startOfDay(Date())
//    }).whereLessThanOrEqualTo("time", when (value) {
//        1 -> endOfDay(Date())
//        2 -> endOfWeek()
//        3 -> endOfMonth()
//        4 -> endOfYear()
//        else -> startOfDay(Date())
//    }).get()

}


fun clickByFilterCommonResult(goalCollection: CollectionReference, position: Int, value: Int): Task<QuerySnapshot> {
    return goalCollection.whereGreaterThanOrEqualTo("time", when (value) {
        1 -> startOfDay(Date())
        2 -> startOfWeek()
        3 -> startOfMonth()
        4 -> startOfYear()
        else -> startOfDay(Date())
    }).whereLessThanOrEqualTo("time", when (value) {
        1 -> endOfDay(Date())
        2 -> endOfWeek()
        3 -> endOfMonth()
        4 -> endOfYear()
        else -> startOfDay(Date())
    }).get()

}

fun clickByFilterCommonResultForEachCenter(goalCollection: CollectionReference, position: Int, value: Int): Task<QuerySnapshot> {
    return goalCollection.whereEqualTo("centers", when (position) {
        1 -> "Kyiv"
        2 -> "Kharkiv"
        3 -> "Dnepr"
        4 -> "Zhytomyr"
        5 -> "Lviv"
        6 -> "Odessa"
        7 -> "Chernigov"
        else -> null
    }).whereGreaterThanOrEqualTo("time", when (value) {
        1 -> startOfDay(Date())
        2 -> startOfWeek()
        3 -> startOfMonth()
        4 -> startOfYear()
        else -> startOfDay(Date())
    }).whereLessThanOrEqualTo("time", when (value) {
        1 -> endOfDay(Date())
        2 -> endOfWeek()
        3 -> endOfMonth()
        4 -> endOfYear()
        else -> startOfDay(Date())
    }).get()

}

fun clickByFilterIndividualResult(noteRefCollection: CollectionReference, period: Int): Task<QuerySnapshot> {
    return noteRefCollection.whereEqualTo("id", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
        FirebaseAuth.getInstance().uid} else null)
    .whereGreaterThanOrEqualTo("time", when (period) {
        1 -> startOfWeek()
        2 -> startOfMonth()
        3 -> startOfYear()
        else -> startOfWeek()
    }).whereLessThanOrEqualTo("time", when (period) {
        1 -> endOfWeek()
        2 -> endOfMonth()
        3 -> endOfYear()
        else -> endOfWeek()
    })
 .get()

}


fun clickByFilter(noteRefCollection: CollectionReference, position: Int, value: Int): Task<QuerySnapshot> {
    return noteRefCollection.whereEqualTo("centers", when (position) {
        1 -> "Kyiv"
        2 -> "Kharkiv"
        3 -> "Dnepr"
        4 -> "Zhytomyr"
        5 -> "Lviv"
        6 -> "Odessa"
        7 -> "Chernigov"
        else -> null
    }).whereGreaterThanOrEqualTo("time", when (value) {
        1 -> startOfDay(Date())
        2 -> startOfWeek()
        3 -> startOfMonth()
        4 -> startOfYear()
        else -> startOfDay(Date())
    }).whereLessThanOrEqualTo("time", when (value) {
        1 -> endOfDay(Date())
        2 -> endOfWeek()
        3 -> endOfMonth()
        4 -> endOfYear()
        else -> startOfDay(Date())
    }).get()

}

fun clickByFilterForEachCenterGoal(noteRefCollection: CollectionReference, position: Int, value: Int): Task<QuerySnapshot> {
    return noteRefCollection.whereEqualTo("center", when (position) {
        1 -> "Kyiv"
        2 -> "Kharkiv"
        3 -> "Dnepr"
        4 -> "Zhytomyr"
        5 -> "Lviv"
        6 -> "Odessa"
        7 -> "Chernigov"
        else -> null
//    }).whereGreaterThanOrEqualTo("time", when (value) {
//        1 -> startOfDay(Date())
//        2 -> startOfWeek()
//        3 -> startOfMonth()
//        4 -> startOfYear()
//        else -> startOfDay(Date())
//    }).whereLessThanOrEqualTo("time", when (value){
//        1 -> endOfDay(Date())
//        2 -> endOfWeek()
//        3 -> endOfMonth()
//        4 -> endOfYear()
//        else -> startOfDay(Date())
    }).get()

}

