package niranjan.kb.materialanimatedtabs

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerAdapter.addFragment(StoryFragment.newInstance())
        viewPagerAdapter.addFragment(MainFragment.newInstance())
        viewPagerAdapter.addFragment(ChatFragment.newInstance())


        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setExpandedAt(1)


        transitionBackground.setOnClickListener {
            Toast.makeText(this, "cxlxjd", Toast.LENGTH_SHORT).show();
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    transitionBackground.setBackgroundColor(
                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple))
                    transitionBackground.alpha = 1 - positionOffset
                } else if (position == 1) {
                    transitionBackground.setBackgroundColor(
                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark))
                    transitionBackground.alpha = positionOffset
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}
