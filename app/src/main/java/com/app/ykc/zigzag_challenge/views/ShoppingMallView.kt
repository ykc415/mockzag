package com.app.ykc.zigzag_challenge.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.app.ykc.zigzag_challenge.R
import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

        ages.text = getAgesString(data.ages)

        val list = data.styles.toList()
        when(list.size) {
            2 -> {
                style1.visibility = View.VISIBLE
                style0.text = list[0]
                style1.text = list[1]
            }
            1-> {
                style1.visibility = View.INVISIBLE
                style0.text = list[0]
            }
            else -> throw IllegalStateException()
        }
    }

    @TextProp
    fun setRank(rankText: CharSequence) {
        ranking.text = rankText
    }

    @CallbackProp
    fun addListener(listener: View.OnClickListener?) {
        root.setOnClickListener { listener?.onClick(it) }
    }


    fun getAgesString(ages: Set<Ages>) : String {
        return ages.map { age ->
            when (age) {
                is Ages.Teens -> context.getString(R.string.teen)
                is Ages.Twenties -> context.getString(R.string.twenties)
                is Ages.Thirties -> context.getString(R.string.thirties)
            }
        }.distinct().let {
            if (it.size == 3) {
                listOf(context.getString(R.string.all))
            } else {
                it
            }
        }.reduce { acc, age ->
            "$acc $age"
        }
    }
}
