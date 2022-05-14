package com.example.firstapplication

import java.time.LocalDate
import java.util.*

public class TimeHelper {


    fun CalculateDay(date : Date) : Day {
        return Day.values()[date.day]
    }

    fun CalculateDay(day : Int, month : Int, year : Int) : Day {
        val dayOfTheWeek : Int = (day + getMonthCode(Month.values()[month - 1]) + getYearCode(year)) % 7
        return Day.values()[dayOfTheWeek]
    }

    fun getMonthCode(month: Month) : Int {
        when(month)
        {
            Month.April, Month.July -> return 0
            Month.January,Month.October -> return 1
            Month.May -> return 2
            Month.August -> return 3
            Month.February, Month.March, Month.November -> return 4
            Month.June -> return 5
            Month.December, Month.September -> return 6
            else -> return -1

        }
    }

    fun getYearCode(year: Int) : Int {
        return (6 + year % 100 + (year % 100) / 4) % 7
    }
}