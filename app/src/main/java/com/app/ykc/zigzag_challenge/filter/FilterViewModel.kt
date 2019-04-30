package com.app.ykc.zigzag_challenge.filter


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.utils.replace

class FilterViewModel(
    state: FilterState

) : MvRxViewModel<FilterState>(state) {

    init {
        logStateChanges()
    }

    fun setData(ages : List<Pair<Ages, Boolean>>, styles : List<Pair<String, Boolean>>) {
        setState {
            copy(ages = ages, styles = styles)
        }
    }

    fun ageChecked(isChecked: Boolean, age: Ages) {
        setState {
            copy(
                    ages = ages.replace(age to isChecked) {it.first == age}
            )
        }
    }

    fun styleChecked(isChecked: Boolean, style: String) {
        setState {
            copy(
                    styles = styles.replace(style to isChecked) {it.first == style}
            )
        }
    }

    fun clear() {
        setState {
            copy(
                ages = ages.map { it.first to false },
                styles = styles.map { it.first to false }
            )
        }
    }

    companion object : MvRxViewModelFactory<FilterViewModel, FilterState> {
        override fun create(viewModelContext: ViewModelContext, state: FilterState): FilterViewModel? {
            return FilterViewModel(state)
        }
    }
}
