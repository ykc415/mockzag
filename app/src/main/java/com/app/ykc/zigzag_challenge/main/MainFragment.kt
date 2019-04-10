package com.app.ykc.zigzag_challenge.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.*
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.withModels
import com.app.ykc.zigzag_challenge.views.emptyView
import com.app.ykc.zigzag_challenge.views.shoppingMallView
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


class MainFragment : BaseMvRxFragment() {

    private val viewModel: MainViewModel by activityViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search.setOnClickListener {

        }

        filter.setOnClickListener {
            findNavController().navigate(R.id.start_filter)
        }

        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    override fun invalidate() {

        withState(viewModel) { state ->
            Timber.e("invalidate ${state.shoppingMalls?.size}")

            if (state.shoppingMallData is Success) {
                label.text = String.format(getString(R.string.ranking), state.week)
            }

            recyclerView.withModels {
                if(state.shoppingMalls != null) {
                    if(state.shoppingMalls.isNotEmpty()) {
                        state.shoppingMalls.forEachIndexed { index, shoppingMall ->
                            shoppingMallView {
                                id(index)
                                data(shoppingMall)
                                rank((index + 1).toString())
                            }
                        }
                    } else {
                        emptyView {
                            id("empty")
                            title("비어있음")
                        }
                    }
                }
            }
        }
    }
}