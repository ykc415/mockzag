package com.app.ykc.zigzag_challenge.main


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.filter.FilterLogic
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.app.ZigZagApplication
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    state: MainState,
    private val repository: ShoppingMallRepository,
    private val filterLogic: FilterLogic

) : MvRxViewModel<MainState>(state) {

    init {
        repository.getShoppingMall()
                .subscribeOn(Schedulers.io())
                .execute(mapper = {
                    it.copy(
                            list = it.list.map { data ->
                                data.copy(
                                    url = ImageUrlGenerator.getImageUrl(data.url)
                                )
                            }.sortedByDescending { d -> d.point }
                    )
                }, stateReducer = {
                    copy(shoppingMallData = it,
                            shoppingMalls = it()?.list,
                            week = it()?.week,
                            styleMap = it()?.list?.map { data -> data.styles }?.flatten()?.associate { style -> style to false },
                            ageMap = it()?.list?.map { data -> data.ages }?.flatten()?.associate { age -> age to false }
                    )
                })
    }

    fun setFilter(ageFilter: Map<Ages, Boolean>, styleFilter: Map<String, Boolean>) {
        setState {

            val result = filterLogic.getFilteredData(shoppingMallData()!!.list,
                    ageFilter.filter { it.value }.keys,
                    styleFilter.filter { it.value }.keys)

            copy(
                    shoppingMalls = result,
                    ageMap =  ageFilter,
                    styleMap =  styleFilter
            )
        }
    }

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {
        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel? {
            return MainViewModel(
                state,
                viewModelContext.app<ZigZagApplication>().repository,
                FilterLogic()
            )
        }
    }
}