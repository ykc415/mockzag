package com.app.ykc.zigzag_challenge


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.app.ZigZagApplication
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        state: MainState,
        private val repository: ShoppingMallRepository

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
                            styleSet = it()?.list?.map { data -> data.styles }?.flatten()?.toSet(),
                            ageSet = it()?.list?.map { data -> data.ages }?.flatten()?.toSet())
                })

    }

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {
        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel? {
            return MainViewModel(state, viewModelContext.app<ZigZagApplication>().repository)
        }
    }
}