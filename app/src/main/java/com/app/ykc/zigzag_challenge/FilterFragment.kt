package com.app.ykc.zigzag_challenge


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.app.ykc.zigzag_challenge.views.AgeChip
import com.app.ykc.zigzag_challenge.views.StyleChip
import kotlinx.android.synthetic.main.fragment_filter.*
import timber.log.Timber


class FilterFragment : BaseMvRxFragment() {

    private val viewModel: MainViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.e("HI")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        select.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    override fun invalidate() {
        withState(viewModel) { state ->

            state.ageSet?.forEach {
                age_group.addView(AgeChip(context!!).apply { setData(it) })
            }
            age_group.visibility = View.VISIBLE

            state.styleSet?.forEach {
                style_group.addView(StyleChip(context!!).apply { setData(it) })
            }
            style_group.visibility = View.VISIBLE

        }
    }
}
