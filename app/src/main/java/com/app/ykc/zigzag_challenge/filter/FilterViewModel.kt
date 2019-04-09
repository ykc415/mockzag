package com.app.ykc.zigzag_challenge.filter


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.app.MainActivity
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.app.ZigZagApplication
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.main.MainState
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import io.reactivex.schedulers.Schedulers

class FilterViewModel(
    state: FilterState

) : MvRxViewModel<FilterState>(state) {

    fun setData(ageFilter : Map<Ages, Boolean>?, styleFilter : Map<String, Boolean>?) {
        setState {
            copy(ageFilter = ageFilter ?: emptyMap(), styleFilter = styleFilter ?: emptyMap())
        }
    }

    fun ageChecked(isChecked: Boolean, age: Ages) {
        setState {
            copy(ageFilter = ageFilter.toMutableMap().apply {
                put(age, isChecked)
            }.toMap())
        }
    }

    fun styleChecked(isChecked: Boolean, style: String) {
        setState {
            copy(styleFilter = styleFilter.toMutableMap().apply {
                put(style, isChecked)
            }.toMap())
        }
    }


    companion object : MvRxViewModelFactory<FilterViewModel, FilterState> {
        override fun create(viewModelContext: ViewModelContext, state: FilterState): FilterViewModel? {
            return FilterViewModel(state)
        }
    }
}