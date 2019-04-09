package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.Range
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.app.ykc.zigzag_challenge.filter.FilterLogic
import org.junit.Test
import org.junit.Assert.*

class FilterLogicTest {

    /**
     *
     *'10대', '20대초반', '20대중반', '20대후반', '30대초반', '30대중반', '30대후반'
     *
     * rawAges = [0, 0, 1, 1, 1, 0, 0]
     * */
    val testData = listOf(
            ShoppingMall(name = "0", url = "", point = 10, rawStyle = "a, b", rawAges = listOf(0, 0, 1, 1, 1, 0, 0)),
            ShoppingMall(name = "1", url = "", point = 9,  rawStyle = "a, b", rawAges = listOf(0, 0, 1, 0, 0, 0, 1)),
            ShoppingMall(name = "2", url = "", point = 8,  rawStyle = "c, d", rawAges = listOf(0, 0, 0, 0, 0, 0, 0)),
            ShoppingMall(name = "3", url = "", point = 7,  rawStyle = "a, b", rawAges = listOf(0, 0, 1, 1, 0, 0, 0)),
            ShoppingMall(name = "4", url = "", point = 6,  rawStyle = "a, b", rawAges = listOf(0, 0, 1, 0, 0, 0, 0)),
            ShoppingMall(name = "5", url = "", point = 5,  rawStyle = "a, b", rawAges = listOf(0, 0, 1, 0, 0, 0, 0)),
            ShoppingMall(name = "6", url = "", point = 4,  rawStyle = "a, c", rawAges = listOf(1, 1, 0, 0, 0, 0, 0)),
            ShoppingMall(name = "7", url = "", point = 3,  rawStyle = "a, e", rawAges = listOf(0, 0, 1, 0, 0, 0, 0)),
            ShoppingMall(name = "8", url = "", point = 2,  rawStyle = "a, d", rawAges = listOf(0, 0, 1, 0, 0, 0, 0)),
            ShoppingMall(name = "9", url = "", point = 1,  rawStyle = "a, b", rawAges = listOf(1, 0, 1, 0, 0, 0, 0))
    )

    val logic = FilterLogic()

    @Test
    fun nothing_selected_case() {
        val expected = logic.getFilteredData(testData, age = emptySet(), style = emptySet())

        assertEquals(expected, testData)
    }

    @Test
    fun one_age_selected_case() {
        val expected = logic.getFilteredData(testData, age = setOf(Ages.Teens), style = emptySet())

        assertEquals(expected, listOf(testData[6], testData[9]))
    }

    @Test
    fun two_age_selected_case() {
        val expected = logic.getFilteredData(testData,
                age = setOf(Ages.Teens, Ages.Thirties(range = Range.Late)),
                style = emptySet())

        assertEquals(expected, listOf(testData[1], testData[6], testData[9]))
    }

    @Test
    fun multiple_age_selected_case() {
        val expected = logic.getFilteredData(testData,
                age = setOf(Ages.Teens,
                      Ages.Thirties(range = Range.Late),
                      Ages.Twenties(range = Range.Late)),
                style = emptySet())

        assertEquals(expected, listOf(testData[0], testData[1], testData[3], testData[6], testData[9]))
    }

    @Test
    fun one_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptySet(),
                style = setOf("c"))

        assertEquals(expected, listOf(testData[2], testData[6]))
    }

    @Test
    fun two_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptySet(),
                style = setOf("c", "d"))

        assertEquals(expected, listOf(testData[2], testData[6], testData[8]))
    }

    @Test
    fun multiple_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptySet(),
                style = setOf("a", "b", "d"))

        assertEquals(expected, listOf(testData[0], testData[1], testData[3], testData[4],
            testData[5], testData[8], testData[9], testData[2], testData[6], testData[7]))
    }

    @Test
    fun age_and_style_selected_case() {
        val expected = logic.getFilteredData(testData, age = setOf(Ages.Twenties(Range.Mid)),
            style = setOf("a", "b"))

        assertEquals(expected, listOf(testData[0], testData[1], testData[3], testData[4],
            testData[5], testData[9], testData[7], testData[8]))
    }
}