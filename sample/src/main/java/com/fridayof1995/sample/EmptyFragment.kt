package com.fridayof1995.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class EmptyFragment : androidx.fragment.app.Fragment() {


    private var fragmentNumber by FragmentArgumentDelegate<Number>()

    companion object {
        fun newInstance(fragmentNumber: Number) = EmptyFragment().apply {
            this.fragmentNumber = fragmentNumber
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        val btFragmentNumber: Button = view.findViewById(R.id.btFragmentNumber) as Button
        btFragmentNumber.text = fragmentNumber.toString()

        return view
    }


}
