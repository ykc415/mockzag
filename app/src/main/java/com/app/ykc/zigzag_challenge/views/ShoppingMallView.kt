package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.shoppingmall.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ShoppingMallView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.shoppingmall, this, true)
    }

    @ModelProp
    fun setData(data: ShoppingMall) {

        Glide.with(context)
                .load(data.url)
                .error(R.drawable.error_image)
                .apply(RequestOptions.circleCropTransform())
                .into(image)

        name.text = data.name
    }

    @TextProp
    fun setRank(rankText: CharSequence) {
        ranking.text = rankText
    }
}