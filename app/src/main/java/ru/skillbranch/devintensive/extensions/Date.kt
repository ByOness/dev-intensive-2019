package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Long, units: TimeUnits): Date {
    this.time += units * value
    return this
}
 
enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    operator fun times(value: Long):Long {
        return value * when(this) {
            SECOND -> 1000L
            MINUTE -> SECOND * 60
            HOUR -> MINUTE * 60
            DAY -> HOUR * 24
        }
    }

    fun plural(value:Long):String {
        return "${abs(value)} " + when (this) {
            SECOND -> {when (pluralForm(value)) {
                0 -> "секунду"
                1 -> "секунды"
                else -> "секунд"
            }
            }
            MINUTE -> {when (pluralForm(value)) {
                0 -> "минуту"
                1 -> "минуты"
                else -> "минут"
            }
            }
            HOUR -> {when (pluralForm(value)) {
                0 -> "час"
                1 -> "часа"
                else -> "часов"
            }
            }
            DAY -> {when (pluralForm(value)) {
                0 -> "день"
                1 -> "дня"
                else -> "дней"
            }
            }
        }
    }

    private fun pluralForm(value:Long):Int {
        val absvalue = abs(value)
        return if (absvalue%10==1L && absvalue%100!=11L) 0
        else if (absvalue%10>=2L && absvalue%10<=4L && (absvalue%100<10L || absvalue%100>=20L)) 1
        else 2
    }
}