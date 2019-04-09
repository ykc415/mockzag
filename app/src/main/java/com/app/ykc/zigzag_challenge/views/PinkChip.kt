package com.app.ykc.zigzag_challenge.views

import android.util.TypedValue
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.app.KotlinModel
import com.google.android.material.chip.Chip

data class PinkChip(
        val title: String,
        val isChecked: Boolean,
        val listener : (Boolean, String) -> Unit

) : KotlinModel(R.layout.chip_style) {

    val chip by bind<Chip>(R.id.chip)

    override fun bind() {
        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)

        chip.text = title
        chip.isChecked = isChecked

        chip.setOnCheckedChangeListener { view, checked ->
            listener(checked, title)
            (view as Chip).post { view.setChipBackgroundColorResource(if(checked) R.color.pink else R.color.white) }
        }
    }

}