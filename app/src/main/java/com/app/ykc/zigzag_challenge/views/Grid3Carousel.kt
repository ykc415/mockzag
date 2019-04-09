package com.app.ykc.zigzag_challenge.views

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Grid3Carousel constructor(context: Context) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager {

        return GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
    }
}