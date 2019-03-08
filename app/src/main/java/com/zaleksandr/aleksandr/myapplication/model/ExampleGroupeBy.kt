package com.zaleksandr.aleksandr.myapplication.model

data class Event(val userId: String, val intro:Int, val oneDay:Int)

val events = listOf(
        Event("Ivan", 5, 14),
        Event("Ivan", 2,13),
        Event("Kolya", 8,12),
        Event("Kolya", 2,11)
)

val itemsToRv = events.
        groupBy { event -> event.userId }.
        mapValues { it.value.sumBy { it.intro } }.
        map { Event(it.key, it.value, it.value) }.
        sortedByDescending { it.intro }