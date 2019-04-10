package com.app.ykc.zigzag_challenge.filter

import com.airbnb.mvrx.MvRxState
import com.app.ykc.zigzag_challenge.data.Ages

data class FilterState(
    val ages : List<Pair<Ages, Boolean>> = emptyList(),
    val styles : List<Pair<String, Boolean>> = emptyList()

) : MvRxState
