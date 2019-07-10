package ru.skillbranch.devintensive

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if (firstName?.isEmpty() == true) firstName = null
        if (lastName?.isEmpty() == true) lastName = null
        return firstName to lastName
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
fun Date.add(value: Int, TimeUnits: String): Date {
    var time = this.time

    when (TimeUnits) {
        "second", "seconds" -> value * SECOND
        "minute", "minutes" -> value * MINUTE
        "hour", "hours" -> value * HOUR
        "day", "days" -> value * DAY
        else -> throw IllegalStateException("invalid units")
    }
    this.time = time
    return this
}