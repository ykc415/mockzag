package com.app.ykc.zigzag_challenge.data

import com.app.ykc.zigzag_challenge.utils.FileReader
import com.squareup.moshi.Moshi
import io.reactivex.Observable

const val FILE_NAME = "shop_list.json"

class ShoppingMallRepository(
        private val moshi : Moshi,
        private val fileReader: FileReader

) {

    fun getShoppingMall() : Observable<ShoppingMallData> {

        val adapter = moshi.adapter(ShoppingMallData::class.java)

        return Observable.just(fileReader.readJsonFileFromAsset(FILE_NAME))
            .map { adapter.fromJson(it) }
    }

}