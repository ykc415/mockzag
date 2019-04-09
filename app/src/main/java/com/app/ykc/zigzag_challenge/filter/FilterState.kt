package com.app.ykc.zigzag_challenge.filter

import com.airbnb.mvrx.MvRxState
import com.app.ykc.zigzag_challenge.data.Ages

data class FilterState(
    val ageFilter : Map<Ages, Boolean> = emptyMap(),
    val styleFilter : Map<String, Boolean> = emptyMap()

) : MvRxState