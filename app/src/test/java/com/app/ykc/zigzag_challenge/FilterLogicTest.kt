package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.algorithm.FileReader
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.Range
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.app.ykc.zigzag_challenge.data.ShoppingMallData
import com.app.ykc.zigzag_challenge.filter.FilterLogic
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test
import org.junit.Assert.*

class FilterLogicTest {

    private val testFileName = "shop_list.json"

    private val testData: List<ShoppingMall>

    init {
        val json = FileReader().readJsonFile(testFileName)

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        testData = moshi.adapter(ShoppingMallData::class.java).fromJson(json)!!.list
    }

    val logic = FilterLogic()

    @Test
    fun nothing_selected_case() {
        val expected = logic.getFilteredData(testData, age = emptyList(), style = emptyList())

        assertEquals(expected, testData)
    }

    @Test
    fun one_age_selected_case() {
        val expected = logic.getFilteredData(testData, age = listOf(Ages.Teens), style = emptyList())

        expected.forEach {
            assertTrue(it.ages.contains(Ages.Teens))
        }
    }

    @Test
    fun two_age_selected_case() {
        val ages = listOf(Ages.Teens, Ages.Thirties(range = Range.Late))

        val expected = logic.getFilteredData(testData,
                age = ages,
                style = emptyList())

        expected.forEach {
            assertTrue(it.ages.contains(Ages.Teens) || it.ages.contains(Ages.Thirties(range = Range.Late)))
        }
    }

    @Test
    fun multiple_age_selected_case() {
        val ages = listOf(Ages.Teens, Ages.Thirties(range = Range.Late), Ages.Twenties(range = Range.Late))

        val expected = logic.getFilteredData(testData,
                age = ages,
                style = emptyList())

        expected.forEach {
            assertTrue(it.ages.contains(Ages.Teens)
                    || it.ages.contains(Ages.Thirties(range = Range.Late))
                    || it.ages.contains(Ages.Twenties(range = Range.Late)))
        }
    }

    @Test
    fun one_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptyList(), style = listOf("러블리"))

        expected.forEach {
            assertTrue(it.styles.contains("러블리"))
        }
    }

    @Test
    fun two_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptyList(), style = listOf("러블리", "페미닌"))

        println(expected.first())
        assertEquals(expected.first().styles, setOf("러블리", "페미닌"))
    }

    @Test
    fun multiple_style_selected_case() {
        val expected = logic.getFilteredData(testData, age =  emptyList(), style = listOf("러블리", "페미닌", "심플베이직"))

        println(expected.first())
        assertEquals(expected.first().styles, setOf("러블리", "심플베이직"))
    }

    @Test
    fun age_and_style_selected_case() {
        val expected = logic.getFilteredData(testData, age = listOf(Ages.Teens), style = listOf("러블리", "페미닌", "심플베이직"))

        expected.forEach {
            assertTrue(it.ages.contains(Ages.Teens))
        }

        println(expected.first())
        assertEquals(expected.first().styles, setOf("러블리", "심플베이직"))
    }
}