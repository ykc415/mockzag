package com.app.ykc.zigzag_challenge.utils

import androidx.annotation.VisibleForTesting

object ImageUrlGenerator {

    const val IMAGE_URL_FORMAT = "https://cf.shop.s.zigzag.kr/images/%1\$s.jpg"

    fun getImageUrl(rawUrl: String): String {
        val symbol = getSymbol(rawUrl)
        return String.format(IMAGE_URL_FORMAT, symbol)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getSymbol(rawUrl: String) : String {
        val strs = rawUrl.split(".")
        return if(strs.first().endsWith("www")) {
            strs[1]
        } else {
            strs.first().split("//").last()
        }
    }

}