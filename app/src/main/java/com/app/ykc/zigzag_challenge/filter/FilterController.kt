package com.app.ykc.zigzag_challenge.filter

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.Typed2EpoxyController
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.views.*

class FilterController(
        private val callback: AdapterCallback

): Typed2EpoxyController<List<Pair<Ages, Boolean>>, List<Pair<String, Boolean>>?>() {

    interface AdapterCallback {
        fun onAgeClicked(isCheck: Boolean, age: Ages)
        fun onStyleClicked(isCheck: Boolean, style: String)
    }

    override fun buildModels(ages: List<Pair<Ages, Boolean>>?, styles: List<Pair<String, Boolean>>?) {
        labelView {
            id("age")
            text("연령대")
        }

        gridCarousel {
            id("ages")

            models(mutableListOf<EpoxyModel<View>>().apply {
                ages?.forEachIndexed { index, data ->
                    add(BlueChip(
                            age = data.first,
                            isChecked = data.second
                    ).apply {
                        listener = callback::onAgeClicked
                    }
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
                styles?.forEachIndexed { index, data ->
                    add(PinkChip(title = data.first,
                            isChecked = data.second).apply {
                        listener = callback::onStyleClicked
                    }
                            .id(data.first)
                    )
                }
            })
        }
    }
}