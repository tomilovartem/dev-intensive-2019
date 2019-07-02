package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val MONTH = 31 * DAY
const val YEAR = 365 * DAY

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.MONTH -> value * MONTH
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
    MONTH,
    YEAR
}

enum class TimeUnitsFull

fun num2date(value: Int, units: TimeUnits): String {
    return when (units) {
        TimeUnits.SECOND -> Utils.num2str(value, arrayOf("секунду", "секунды", "секунд"))
        TimeUnits.MINUTE -> Utils.num2str(value, arrayOf("минуту", "минуты", "минут"))
        TimeUnits.HOUR -> Utils.num2str(value, arrayOf("час", "часа", "часов"))
        TimeUnits.DAY -> Utils.num2str(value, arrayOf("день", "дня", "дней"))
        TimeUnits.MONTH -> Utils.num2str(value, arrayOf("месяц", "месяца", "месяцев"))
        TimeUnits.YEAR -> Utils.num2str(value, arrayOf("год", "года", "лет"))

    }
}

fun Date.humanizeDiff(date: Date = Date()):String {
    val timeDiff = (date.time - this.time)
    val absTimeDiff = abs(timeDiff)
    val before = if(timeDiff<0) "через " else ""
    val after = if(timeDiff>0) " назад" else ""

    return "$before${when(absTimeDiff) {
        in 0..1 * SECOND -> return "только что"
        in 1 * SECOND +1 ..45 * SECOND -> "несколько ${num2date(5, TimeUnits.SECOND)}"
        in 45 * SECOND + 1..75 * SECOND -> num2date(1, TimeUnits.MINUTE)
        in 75 * SECOND + 1..45 * MINUTE -> "${(absTimeDiff / MINUTE).toInt()} ${num2date((absTimeDiff / MINUTE).toInt(), TimeUnits.MINUTE)}"
        in 45 * MINUTE + 1..75 * MINUTE -> num2date(1, TimeUnits.HOUR)
        in 75 * MINUTE + 1..22 * HOUR -> "${(absTimeDiff / HOUR).toInt()} ${num2date((absTimeDiff / HOUR).toInt(), TimeUnits.HOUR)}"
        in 22 * HOUR + 1 .. 26 * HOUR -> num2date(1, TimeUnits.DAY)
        in 26 * HOUR + 1 .. 360 * DAY -> "${(absTimeDiff / DAY).toInt()} ${num2date((absTimeDiff / DAY).toInt(), TimeUnits.DAY)}"
        else -> return if(timeDiff<0) "более чем через год" else "более года назад"
    }}$after"

}