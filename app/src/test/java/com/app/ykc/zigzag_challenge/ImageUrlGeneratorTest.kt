package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.algorithm.FileReader
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.app.ykc.zigzag_challenge.data.ShoppingMallData
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test

class ImageUrlGeneratorTest {

    private val testFileName = "shop_list.json"

    private val testData: List<ShoppingMall>

    init {
        val json = FileReader().readJsonFile(testFileName)

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        testData = moshi.adapter(ShoppingMallData::class.java).fromJson(json)!!.list
    }

    @Test
    fun getImageUrlTest() {
        testData.map {
            it.url
        }.forEach { url ->
            val result = ImageUrlGenerator.getImageUrl(url)
            println(result)
        }
    }

    @Test
    fun getSymbol() {
        testData.map {
            it.url
        }.forEach { url ->
            val expected = ImageUrlGenerator.getSymbol(url)
            println(expected)
        }
    }
}