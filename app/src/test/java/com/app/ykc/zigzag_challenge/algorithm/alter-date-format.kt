package com.app.ykc.zigzag_challenge.algorithm

import org.junit.Assert.assertEquals
import org.junit.Test

object TimeCalculator {

    fun afterSeconds(time: String, intervalSeconds: Long): String {
        val strArr = time.split(" ")
        val timeArr = strArr[1].split(":")

        val isPM = strArr[0] == "PM"

        val hour = timeArr[0].let {
            if (isPM) it.toLong() + 12L
            else it.toLong().let { long ->
                if (long == 12L) 0L
                else long
            }
        }

        println(hour)

        val min = timeArr[1].toLong()
        val sec = timeArr[2].toLong()

        val secs = hour * 3600 + min * 60 + sec + intervalSeconds

        val afterHour = secs / 3600 % 24
        val afterMins = secs % 3600
        val afterMin = afterMins / 60
        val afterSec = afterMins % 60

        return  String.format("%1$02d:%2$02d:%3$02d", afterHour, afterMin, afterSec)
    }
}

class TimeCalculatorTest {

    @Test
    fun test0() {
        val timeInput = "PM 01:00:00"
        val intervalSecondsInput = 10L

        assertEquals("13:00:10", TimeCalculator.afterSeconds(timeInput, intervalSecondsInput))
    }

    @Test
    fun test1() {
        val timeInput = "PM 11:59:59"
        val intervalSecondsInput = 1L

        assertEquals("00:00:00", TimeCalculator.afterSeconds(timeInput, intervalSecondsInput))
    }

    @Test
    fun test2() {
        val timeInput = "AM 12:10:00"
        val intervalSecondsInput = 40L

        assertEquals("00:10:40", TimeCalculator.afterSeconds(timeInput, intervalSecondsInput))
    }

    @Test
    fun test3() {
        val timeInput = "AM 05:24:03"
        val intervalSecondsInput = 102392L

        assertEquals("09:50:35", TimeCalculator.afterSeconds(timeInput, intervalSecondsInput))
    }

}