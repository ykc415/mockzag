package com.app.ykc.zigzag_challenge


import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.app.ykc.zigzag_challenge.app.MvRxViewModel
import com.app.ykc.zigzag_challenge.app.ZigZagApplication
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.utils.ImageUrlGenerator
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

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
                                data.copy(url = ImageUrlGenerator.getImageUrl(data.url))
                            }
                    )
                }, stateReducer = {
                    Timber.e(it.toString())
                    copy(shoppingMallData = it, shoppingMalls = it()?.list, week = it()?.week)
                })

    }

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {
        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel? {
            return MainViewModel(state, viewModelContext.app<ZigZagApplication>().repository)
        }
    }
}