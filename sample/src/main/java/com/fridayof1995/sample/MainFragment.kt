package com.fridayof1995.sample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.fridayof1995.tabanimation.SnapTabLayout


class MainFragment : androidx.fragment.app.Fragment() {


    private var fragmentNumber by FragmentArgumentDelegate<Number>()

    companion object {
        fun newInstance(fragmentNumber: Number) = MainFragment().apply {
            this.fragmentNumber = fragmentNumber
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        val btFragmentNumber: Button = view.findViewById(R.id.btFragmentNumber) as Button
        val backdropCard: RelativeLayout = view.findViewById(R.id.backdropCard) as RelativeLayout

        val numTab = activity?.intent?.getIntExtra("numOfTabs", 3)
        if (numTab!!.equals(SnapTabLayout.NumOfTabs.FIVE.value)) {
            if (fragmentNumber == 2) {
                backdropCard.visibility = View.INVISIBLE
            }
        } else if (numTab.equals(SnapTabLayout.NumOfTabs.THREE.value)) {
            if (fragmentNumber == 1) {
                backdropCard.visibility = View.INVISIBLE
            }
        }

        btFragmentNumber.text = fragmentNumber.toString()
        btFragmentNumber.setOnClickListener {
            context?.toast("Fragment number $fragmentNumber")
        }
        return view
    }


}
