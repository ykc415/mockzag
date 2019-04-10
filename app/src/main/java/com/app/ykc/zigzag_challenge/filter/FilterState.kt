package com.app.ykc.zigzag_challenge.filter

import com.airbnb.mvrx.MvRxState
import com.app.ykc.zigzag_challenge.data.Ages

data class FilterState(
    val ages : List<Pair<Ages, Boolean>>? = null,
    val styles : List<Pair<String, Boolean>>? = null,
    val dirtyFlag : Int = 0

) : MvRxState {

    val selectedAge : MutableSet<Ages> = mutableSetOf()
    val selectedStyle : MutableSet<String> = mutableSetOf()

}
