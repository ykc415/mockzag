package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.getString
import com.app.ykc.zigzag_challenge.utils.dpToPx
import kotlinx.android.synthetic.main.chip_age.view.*
import timber.log.Timber

class AgeChip @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.chip_age, this, true)

        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)

        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            Timber.e("$isChecked")
            if(isChecked) {
                chip.setChipBackgroundColorResource(R.color.soda)
            } else {
                chip.setChipBackgroundColorResource(R.color.white)
            }
        }
    }

    fun setData(data: Ages) {
        chip.text = data.getString(context)
    }

    fun addListener() {

    }

}