package com.fridayof1995.sample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MainFragment : Fragment() {


    private var fragmentNumber by FragmentArgumentDelegate<Number>()

    companion object {
        fun newInstance(fragmentNumber: Number) = MainFragment().apply {
            this.fragmentNumber = fragmentNumber
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        val btFragmentNumber: Button = view.findViewById(R.id.btFragmentNumber) as Button
        val backdropCard: CardView = view.findViewById(R.id.backdropCard) as CardView

        if (fragmentNumber == 1) {
            backdropCard.visibility = View.INVISIBLE
        }

        btFragmentNumber.text = fragmentNumber.toString()
        btFragmentNumber.setOnClickListener {
            context?.toast("Fragment number $fragmentNumber")
        }
        return view
    }


}
