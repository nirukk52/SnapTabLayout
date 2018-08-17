package com.fridayof1995.sample


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.fridayof1995.tabanimation.hideKeyboardFrom
import kotlinx.android.synthetic.main.activity_main.*


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
        val btSetExpands: TextView = view.findViewById(R.id.btSetExpands) as TextView
        val etExpandsAt: EditText = view.findViewById(R.id.etExpandsAt) as EditText
        val backdropCard: CardView = view.findViewById(R.id.backdropCard) as CardView

        if (fragmentNumber == 1 ||fragmentNumber == 4) {
            backdropCard.visibility = View.INVISIBLE
        }

        btFragmentNumber.text = fragmentNumber.toString()
        btFragmentNumber.setOnClickListener {
            context?.toast("Fragment number $fragmentNumber")
        }
        btSetExpands.setOnClickListener() {
            activity?.tabLayout?.expandAt(etExpandsAt.text.toString().toInt())
            hideKeyboardFrom(context,view)
        }

        return view
    }


}
