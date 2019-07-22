package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    this.time +=when (units){
        TimeUnits.SECOND-> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY
    }
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = this.time - date.time

    return if (diff >= 0) {
        humanizeFuture(abs(diff))
    } else {
        humanizePast(abs(diff))
    }
}
private fun humanizeFuture(diff: Long): String {
    /**
    0с - 1с "только что"
    1с - 45с "через несколько секунд"
    45с - 75с "через минуту"
    75с - 45мин "через N минуту;минуты;минут"
    45мин - 75мин "через час"
    75мин 22ч "через N час;часа;часов"
    22ч - 26ч "через день"
    26ч - 360д "через N день;дня;дней"
    >360д "более чем через год"
     */

    return when (diff) {
        in 0..(1 * SECOND) -> "только что"
        in (1 * SECOND + 1)..(45 * SECOND) -> "через несколько секунд"
        in (45 * SECOND + 1)..(75 * SECOND) -> "через минуту"
        in (75 * SECOND + 1)..(45 * MINUTE) -> {
            val amount = calcDiffAmount(diff, MINUTE)
            "через $amount ${Utils.getPluralForm("минуту;минуты;минут", amount)}"
        }
        in (45 * MINUTE + 1)..(75 * MINUTE) -> "через час"
        in (75 * MINUTE + 1)..(22 * HOUR) -> {
            val amount = calcDiffAmount(diff, HOUR)
            "через $amount ${Utils.getPluralForm("час;часа;часов", amount)}"
        }
        in (22 * HOUR + 1)..(26 * HOUR) -> "через день"
        in (26 * HOUR + 1)..(360 * DAY) -> {
            val amount = calcDiffAmount(diff, DAY)
            "через $amount ${Utils.getPluralForm("день;дня;дней", amount)}"
        }
        else -> "более чем через год"
    }
}

private fun humanizePast(diff: Long): String {
    /**
    0с - 1с "только что"
    1с - 45с "несколько секунд назад"
    45с - 75с "минуту назад"
    75с - 45мин "N минуту;минуты;минут назад"
    45мин - 75мин "час назад"
    75мин 22ч "N час;часа;часов назад"
    22ч - 26ч "день назад"
    26ч - 360д "N день;дня;дней назад"
    >360д "более года назад"
     */

    return when (diff) {
        in 0..(1 * SECOND) -> "только что"
        in (1 * SECOND + 1)..(45 * SECOND) -> "несколько секунд назад"
        in (45 * SECOND + 1)..(75 * SECOND) -> "минуту назад"
        in (75 * SECOND + 1)..(45 * MINUTE) -> {
            val amount = calcDiffAmount(diff, MINUTE)
            "$amount ${Utils.getPluralForm("минуту;минуты;минут", amount)} назад"
        }
        in (45 * MINUTE + 1)..(75 * MINUTE) -> "час назад"
        in (75 * MINUTE + 1)..(22 * HOUR) -> {
            val amount = calcDiffAmount(diff, HOUR)
            "$amount ${Utils.getPluralForm("час;часа;часов", amount)} назад"
        }
        in (22 * HOUR + 1)..(26 * HOUR) -> "день назад"
        in (26 * HOUR + 1)..(360 * DAY) -> {
            val amount = calcDiffAmount(diff, DAY)
            "$amount ${Utils.getPluralForm("день;дня;дней", amount)} назад"
        }
        else -> "более года назад"
    }
}

private fun calcDiffAmount(diff: Long, timeInterval: Long): Int {
    val result: Float = (diff.toFloat() / timeInterval.toFloat())
    return result.roundToInt()
}


enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}