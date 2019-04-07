package com.app.ykc.zigzag_challenge.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShoppingMallData(
    @Json(name = "week") val week: String,
    @Json(name = "list") val list: List<ShoppingMall>
)

@JsonClass(generateAdapter = true)
data class ShoppingMall(
        @Json(name = "n") val name: String,
        @Json(name = "u") val url: String,
        @Json(name = "S") val rawStyle: String,
        @Json(name = "0") val point: Long,
        @Json(name = "A") val rawAges: List<Int>
)

