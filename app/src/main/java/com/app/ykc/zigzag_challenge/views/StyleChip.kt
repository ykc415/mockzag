package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.app.ykc.zigzag_challenge.R
import kotlinx.android.synthetic.main.chip_style.view.*
import timber.log.Timber

class StyleChip @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.chip_style, this, true)

        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)

        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            Timber.e("$isChecked")
            if(isChecked) {
                chip.setChipBackgroundColorResource(R.color.pink)
            } else {
                chip.setChipBackgroundColorResource(R.color.white)
            }
        }
    }

    fun setData(style: String) {
        chip.text = style
    }

    fun addListener() {

    }

}