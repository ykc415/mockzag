package com.app.ykc.zigzag_challenge.main

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.app.ykc.zigzag_challenge.data.ShoppingMallData

data class MainState(
    val shoppingMallData: Async<ShoppingMallData> = Uninitialized,
    val shoppingMalls: List<ShoppingMall>? = null,
    val week: String? = null,
    val styleMap: Map<String, Boolean>? = null,
    val ageMap: Map<Ages, Boolean>? = null

) : MvRxState