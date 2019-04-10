package com.app.ykc.zigzag_challenge.views

import android.util.TypedValue
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.OnModelClickListener
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.KotlinModel
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.getString
import com.google.android.material.chip.Chip
import timber.log.Timber

data class BlueChip(
    val age: Ages,
    val isChecked: Boolean

) : KotlinModel(R.layout.chip_age) {

    var listener : ((Boolean, Ages) -> Unit)? = null

    val chip by bind<Chip>(R.id.chip)

    override fun bind() {
        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)

        chip.isChecked = isChecked
        chip.text = age.getString(chip.context)

        chip.setChipBackgroundColorResource(if(isChecked) R.color.soda else R.color.white)

        chip.setOnCheckedChangeListener { view, checked ->
            listener?.invoke(checked, age)

            (view as Chip).post { view.setChipBackgroundColorResource(if(checked) R.color.soda else R.color.white) }

        }
    }

    override fun unbind(view: View) {
        listener = null
        super.unbind(view)
    }

    override fun bind(view: View, previouslyBoundModel: EpoxyModel<*>) {
        Timber.e("$view, $previouslyBoundModel")

        super.bind(view, previouslyBoundModel)

    }

}