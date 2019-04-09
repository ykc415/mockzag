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

    private fun <T> updateWithFilter(list: List<Pair<T, Boolean>>, filter: Set<T>): List<Pair<T, Boolean>> {
        return list.map {
            if (filter.contains(it.first)) {
                it.first to true
            } else {
                it.first to false
            }
        }
    }

    fun setFilter(ageFilter: Set<Ages>, styleFilter: Set<String>) {
        setState {

            val result = filterLogic.getFilteredData(shoppingMallData()!!.list, ageFilter, styleFilter)

            copy(
                    shoppingMalls = result,
                    ages = updateWithFilter(ages!!, ageFilter),
                    styles = updateWithFilter(styles!!, styleFilter)
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