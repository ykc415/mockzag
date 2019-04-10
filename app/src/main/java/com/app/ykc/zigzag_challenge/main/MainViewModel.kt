package com.app.ykc.zigzag_challenge.main


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.filter.FilterLogic
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.app.ZigZagApplication
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    state: MainState,
    private val repository: ShoppingMallRepository,
    private val filterLogic: FilterLogic

) : MvRxViewModel<MainState>(state) {

    init {
        logStateChanges()

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
                            styles = it()?.list?.flatMap {d -> d.styles }?.toSet()?.toList()?.map{s -> s to false},
                            ages = it()?.list?.flatMap {d -> d.ages }?.toSet()?.toList()?.map{a -> a to false}
                    )
                })
    }


    fun setFilter(ageFilter: List<Pair<Ages, Boolean>>, styleFilter: List<Pair<String, Boolean>>) {
        setState {

            val result = filterLogic.getFilteredData(shoppingMallData()!!.list,
                    ageFilter.filter { it.second }.map { it.first },
                    styleFilter.filter { it.second }.map { it.first })

            copy(
                    shoppingMalls = result,
                    ages = ageFilter,
                    styles = styleFilter
            )

        }
    }

    fun remove(data: ShoppingMall) {
        setState {
            copy(shoppingMalls = shoppingMalls?.filter { it != data })
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