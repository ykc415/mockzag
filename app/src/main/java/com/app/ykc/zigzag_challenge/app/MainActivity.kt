package com.app.ykc.zigzag_challenge.app

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity
import com.app.ykc.zigzag_challenge.R

class MainActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
