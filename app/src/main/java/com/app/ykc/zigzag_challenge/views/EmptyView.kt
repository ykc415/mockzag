package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.app.ykc.zigzag_challenge.R
import kotlinx.android.synthetic.main.view_empty.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true)
    }


    @TextProp
    fun setTitle(str: CharSequence) {
        title.text = str
    }
}