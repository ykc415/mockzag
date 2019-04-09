package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.app.ykc.zigzag_challenge.R
import kotlinx.android.synthetic.main.chip_style.view.*

class StyleChip @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var listener: (Boolean, String) -> Unit
    lateinit var style: String

    init {
        LayoutInflater.from(context).inflate(R.layout.chip_style, this, true)

        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)

        chip.setOnCheckedChangeListener { _, isChecked ->

            listener(isChecked, style)

            updateChipBackgroundColor(isChecked)
        }
    }

    private fun updateChipBackgroundColor(isChecked: Boolean) {
        val res = if(isChecked) R.color.pink else R.color.white
        chip.setChipBackgroundColorResource(res)
    }

    fun setData(style: String, isChecked: Boolean) {
        this.style = style

        chip.text = style
        chip.isChecked = isChecked

        updateChipBackgroundColor(isChecked)
    }

    fun clear() {
        chip.isChecked = false

        updateChipBackgroundColor(false)
    }


    fun addListener(listener: (Boolean, String) -> Unit) {
        this.listener = listener
    }

}