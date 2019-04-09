package com.app.ykc.zigzag_challenge.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.mvrx.*
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.withModels
import com.app.ykc.zigzag_challenge.main.MainViewModel
import com.app.ykc.zigzag_challenge.views.*
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
            viewModel.clear()
        }

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        select.setOnClickListener {
            withState(viewModel) { state ->
                activityViewModel.setFilter(state.selectedAge, state.selectedStyle)
                findNavController().popBackStack()
            }
        }

        withState(activityViewModel) {
            viewModel.setData(it.ages, it.styles)
        }
    }

    override fun invalidate() {
        Timber.e("invalidate ")

        withState(viewModel) { state ->

            recyclerView.withModels {
                labelView {
                    id("age")
                    text("연령대")
                }

                gridCarousel {
                    id("ages")
                    models(mutableListOf<EpoxyModel<View>>().apply {
                        state.ages?.toList()?.forEachIndexed { index, data ->
                            add(BlueChip(age = data.first,
                                    isChecked = data.second,
                                    listener = { checked, age ->
                                        Timber.e("${this@FilterFragment} / $checked, $age")
                                        viewModel.ageChecked(checked, age)
                                    })
                                    .id("age$index")
                            )
                        }
                    })
                }

                labelView {
                    id("style")
                    text("스타일")
                }

                grid3Carousel {
                    id("styles")
                    models(mutableListOf<EpoxyModel<View>>().apply {
                        state.styles?.toList()?.forEachIndexed { index, data ->
                            add(PinkChip(title = data.first,
                                    isChecked = data.second,
                                    listener = { checked, style ->
                                        Timber.e("${this@FilterFragment} / $checked, $style")
                                        viewModel.styleChecked(checked, style)
                                    })
                                    .id("styles$index")
                            )
                        }
                    })
                }
            }
        }
    }

}
