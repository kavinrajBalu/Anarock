package com.anarock.cpsourcing.utilities

import com.google.android.gms.common.internal.Preconditions.checkArgument
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    fun getTimeTwentyMinutesAhead(calendar: Calendar): Calendar {
        val temp = Calendar.getInstance()
        temp.timeInMillis = calendar.timeInMillis
        temp.add(Calendar.MINUTE, 20)
        return temp
    }

    fun getTimeOneHourAhead(calendar: Calendar): Calendar {
        val temp = Calendar.getInstance()
        temp.timeInMillis = calendar.timeInMillis
        temp.add(Calendar.HOUR, 1)
        return temp
    }

    fun getTimeEightHourAhead(calendar: Calendar): Calendar {
        val temp = Calendar.getInstance()
        temp.timeInMillis = calendar.timeInMillis
        temp.add(Calendar.HOUR, 8)
        temp.set(Calendar.MINUTE, 0)
        return temp
    }

    fun getTimeFiveHourAhead(calendar: Calendar): Calendar {
        val temp = Calendar.getInstance()
        temp.timeInMillis = calendar.timeInMillis
        temp.add(Calendar.HOUR, 5)
        temp.set(Calendar.MINUTE, 0)
        return temp
    }


    fun getCustomDateFormat(format: String, calendar: Calendar): String {
        val simpleDateFormat = SimpleDateFormat(format)
        simpleDateFormat.timeZone = TimeZone.getDefault()
        val date = calendar.time
        return simpleDateFormat.format(date)
    }

    fun isItWorkingTime(calendar: Calendar):Boolean {
        val officeStartTime  = Calendar.getInstance()
        officeStartTime.set(Calendar.HOUR,10)
        officeStartTime.set(Calendar.MINUTE,0)
        officeStartTime.set(Calendar.SECOND,0)
        officeStartTime.set(Calendar.AM_PM,Calendar.AM)

        val officeEndTime = Calendar.getInstance()
        officeEndTime.set(Calendar.HOUR,20)
        officeEndTime.set(Calendar.MINUTE,0)
        officeEndTime.set(Calendar.SECOND,0)
        officeEndTime.set(Calendar.AM_PM,Calendar.AM)

        return calendar.time.after(officeStartTime.time) && calendar.time.before(officeEndTime.time)
    }

    fun isItAfterWorkingTime(calendar: Calendar):Boolean
    {
        val officeEndTime = Calendar.getInstance()
        officeEndTime.set(Calendar.HOUR,20)
        officeEndTime.set(Calendar.MINUTE,0)
        officeEndTime.set(Calendar.SECOND,0)
        officeEndTime.set(Calendar.AM_PM,Calendar.AM)
        return  calendar.time.after(officeEndTime.time)
    }

    fun isItElevenAM(calendar: Calendar):Boolean
    {
        val elevenAm  = Calendar.getInstance()
        elevenAm.set(Calendar.HOUR,11)
        elevenAm.set(Calendar.MINUTE,0)
        elevenAm.set(Calendar.SECOND,0)
        elevenAm.set(Calendar.MILLISECOND,0)
        elevenAm.set(Calendar.AM_PM,Calendar.AM)

        val scheduleTime = Calendar.getInstance()
        scheduleTime.timeInMillis = calendar.timeInMillis
        scheduleTime.set(Calendar.MINUTE, 0)
        scheduleTime.set(Calendar.SECOND,0)
        scheduleTime.set(Calendar.MILLISECOND,0)
        return scheduleTime == elevenAm
    }

    fun customDateTimeString(pattern : String ,calendar: Calendar):String
    {
        val format = SimpleDateFormat(pattern)
        return format.format(calendar.time)
    }

    fun getSecondsDifference(startTime: Date, endtime: Date): Long {
        val diff: Long = endtime.time - startTime.time
        return (diff / 1000) % 60
    }

    fun getCurrentDate() :String
    {
        val calender = Calendar.getInstance()
        calender.add(Calendar.DATE,2)
        return (SimpleDateFormat("E,MMM d").format(calender.time)+ getDayOfMonthSuffix(Calendar.DAY_OF_MONTH)).toUpperCase()
    }

    private fun getDayOfMonthSuffix(n: Int): String? {
        checkArgument(n in 1..31, "illegal day of month: $n")
        return if (n in 11..13) {
            "th"
        } else when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    fun isToday(date : String):Boolean
    {
        val callLogDate = Calendar.getInstance()
        callLogDate.timeInMillis = date.toLong()
        val currentDate = Calendar.getInstance()
        return callLogDate.get(Calendar.DAY_OF_YEAR) === currentDate.get(Calendar.DAY_OF_YEAR) &&
                callLogDate.get(Calendar.YEAR) === currentDate.get(Calendar.YEAR)
    }
}