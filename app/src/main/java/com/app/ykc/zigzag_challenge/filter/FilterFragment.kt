package com.app.ykc.zigzag_challenge.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelTouchCallback
import com.airbnb.epoxy.EpoxyTouchHelper
import com.airbnb.epoxy.SimpleEpoxyController
import com.airbnb.mvrx.*
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.withModels
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.getString
import com.app.ykc.zigzag_challenge.main.MainViewModel
import com.app.ykc.zigzag_challenge.views.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.shoppingmall.*
import timber.log.Timber


class FilterFragment : BaseMvRxFragment(), FilterController.AdapterCallback{

    private val activityViewModel: MainViewModel by activityViewModel()
    private val viewModel: FilterViewModel by fragmentViewModel()

    val controller = FilterController(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clear.setOnClickListener {
            viewModel.clear()
        }

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        select.setOnClickListener {
            withState(viewModel) { state ->
                activityViewModel.setFilter(state.ages, state.styles)

                findNavController().popBackStack()
            }
        }

        if (savedInstanceState == null) {
            withState(activityViewModel) {
                viewModel.setData(it.ages!!, it.styles!!)
            }
        }

        recyclerView.setController(controller)

    }

    override fun invalidate() {
        Timber.e("invalidate ")

        withState(viewModel) { state ->
            controller.setData(state.ages, state.styles)
        }
    }

    override fun onAgeClicked(isCheck: Boolean, age: Ages) {
        Timber.e("$isCheck, $age")
        viewModel.ageChecked(isCheck, age)
    }

    override fun onStyleClicked(isCheck: Boolean, style: String) {
        Timber.e("$isCheck, $style")
        viewModel.styleChecked(isCheck, style)
    }

}
