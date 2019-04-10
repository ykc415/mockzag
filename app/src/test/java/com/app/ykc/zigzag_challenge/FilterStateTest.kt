package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.Range
import com.app.ykc.zigzag_challenge.filter.FilterState
import com.app.ykc.zigzag_challenge.utils.replace
import org.junit.Test
import org.junit.Assert.*


class FilterStateTest {

    @Test
    fun equalsTest() {

        val a = FilterState(ages = listOf(Ages.Teens to false, Ages.Twenties(range = Range.Mid) to false),
                styles = listOf("스타일" to false, "스타일2" to false))

        val b = FilterState(ages = listOf(Ages.Teens to false, Ages.Twenties(range = Range.Mid) to false),
                styles = listOf("스타일" to false, "스타일2" to false))

        assertEquals(a, b)

    }

    @Test
    fun replaceTest() {
        val a = FilterState(ages = listOf(Ages.Teens to false, Ages.Twenties(range = Range.Mid) to false),
                styles = listOf("스타일" to false, "스타일2" to false))

        val b = FilterState(ages = listOf(Ages.Teens to false, Ages.Twenties(range = Range.Mid) to false),
                styles = listOf("스타" to false, "스타2" to false))

        val c = a.copy(styles = a.styles.map { it.copy(it.first.replace("일", "")) })

        assertEquals(c, b)
    }

    @Test
    fun agesTest() {
        val ages = listOf<Pair<Ages, Boolean>>(Ages.Teens to false)
        val age = Ages.Teens
        val isChecked = true

        val result = ages.map { it.copy(second = if(it.first == age) isChecked else it.second) }

        assertEquals(result, listOf<Pair<Ages, Boolean>>(Ages.Teens to true))
    }
}