package com.app.ykc.zigzag_challenge.filter


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.data.Ages

import timber.log.Timber

class FilterViewModel(
    state: FilterState

) : MvRxViewModel<FilterState>(state) {

    init {
        logStateChanges()
    }

    fun setData(ages : List<Pair<Ages, Boolean>>?, styles : List<Pair<String, Boolean>>?) {
        Timber.e("""
            $ages
            $styles
        """.trimIndent())

        setState {
            copy(ages = ages, styles = styles)
        }
    }

    fun ageChecked(isChecked: Boolean, age: Ages) {
        withState {
            if(isChecked) {
                it.selectedAge.add(age)
            } else {
                it.selectedAge.remove(age)
            }
        }
    }

    fun styleChecked(isChecked: Boolean, style: String) {
        withState {
            if(isChecked) {
                it.selectedStyle.add(style)
            } else {
                it.selectedStyle.remove(style)
            }
        }
    }

    fun clear() {
        setState {
            copy(
                ages = ages?.map { it.first to false },
                styles = styles?.map { it.first to false }
            ).apply {
                this.selectedAge.clear()
                this.selectedStyle.clear()
            }
        }
    }

    companion object : MvRxViewModelFactory<FilterViewModel, FilterState> {
        override fun create(viewModelContext: ViewModelContext, state: FilterState): FilterViewModel? {
            return FilterViewModel(state)
        }
    }
}