package com.app.ykc.zigzag_challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.airbnb.mvrx.*
import com.app.ykc.zigzag_challenge.app.withModels
import com.app.ykc.zigzag_challenge.views.shoppingMallView
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


class MainFragment : BaseMvRxFragment() {

    private val viewModel: MainViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search.setOnClickListener {

        }

        filter.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
        }

        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    override fun invalidate() = withState(viewModel) { state ->
        Timber.e("invalidate $state")

        if(state.shoppingMallData is Success) {
            label.text = String.format(getString(R.string.ranking), state.week)
        }

        recyclerView.withModels {
            state.shoppingMalls?.forEachIndexed { index, shoppingMall ->
                shoppingMallView {
                    id(index)
                    data(shoppingMall)
                    rank((index+1).toString())
                }
            }
        }
    }
}