package com.app.ykc.zigzag_challenge.views

import android.util.TypedValue
import android.view.View
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.KotlinModel
import com.google.android.material.chip.Chip

data class PinkChip(
        val title: String,
        val isChecked: Boolean
) : KotlinModel(R.layout.chip_style) {

    var listener : ((Boolean, String) -> Unit)? = null

    val chip by bind<Chip>(R.id.chip)

    override fun bind() {
        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)

        chip.text = title
        chip.isChecked = isChecked
        chip.setChipBackgroundColorResource(if(isChecked) R.color.pink else R.color.white)

        chip.setOnCheckedChangeListener { view, checked ->
            listener?.invoke(checked, title)
            (view as Chip).post { view.setChipBackgroundColorResource(if(checked) R.color.pink else R.color.white) }
        }
    }

    override fun unbind(view: View) {
        listener = null
        super.unbind(view)
    }
}