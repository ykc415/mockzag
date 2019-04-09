package com.app.ykc.zigzag_challenge.utils

import android.content.res.AssetManager

class FileReader(
        private val asset: AssetManager

) {

    fun readJsonFileFromAsset(fileName: String): String {
        return asset.open(fileName).bufferedReader().use { it.readText() }
    }

}