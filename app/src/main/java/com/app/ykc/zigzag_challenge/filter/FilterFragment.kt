package com.app.ykc.zigzag_challenge.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.main.MainViewModel
import com.app.ykc.zigzag_challenge.views.AgeChip
import com.app.ykc.zigzag_challenge.views.StyleChip
import kotlinx.android.synthetic.main.fragment_filter.*
import timber.log.Timber


class FilterFragment : BaseMvRxFragment() {

    private val activityViewModel: MainViewModel by activityViewModel()
    private val viewModel: FilterViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clear.setOnClickListener {
            withState(viewModel) { state ->
                state.ageFilter.mapValues { false }
                state.styleFilter.mapValues { false }
            }

            age_group.children.forEach {
                (it as AgeChip).clear()
            }
            style_group.children.forEach {
                (it as StyleChip).clear()
            }
        }

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        select.setOnClickListener {
            withState(viewModel) { state ->
                activityViewModel.setFilter(state.ageFilter, state.styleFilter)
                findNavController().popBackStack()
            }
        }

        withState(activityViewModel) {
            viewModel.setData(it.ageMap, it.styleMap)
        }
    }

    override fun invalidate() {
        Timber.e("invalidate ")

        withState(viewModel) { state ->

            if (age_group.childCount == 0) {
                state.ageFilter.forEach {
                    age_group.addView(AgeChip(context!!).apply {
                        addListener { isChecked, age -> viewModel.ageChecked(isChecked, age) }
                        setData(it.key, it.value)
                    })
                }
                age_group.visibility = View.VISIBLE
            }

            if(style_group.childCount == 0) {
                state.styleFilter.forEach {
                    style_group.addView(StyleChip(context!!).apply {
                        addListener { isChecked, style -> viewModel.styleChecked(isChecked, style) }
                        setData(it.key, it.value)
                    })
                }
                style_group.visibility = View.VISIBLE
            }
        }
    }

}
