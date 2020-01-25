package com.fridayof1995.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.fridayof1995.tabanimation.SnapTabLayout
import kotlinx.android.synthetic.main.activity_launcher.*


class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)


        val numOfTabs = ArrayList<Int>()//Creating an empty arraylist
        numOfTabs.add(SnapTabLayout.NumOfTabs.THREE.value)//Adding object in arraylist
        numOfTabs.add(SnapTabLayout.NumOfTabs.FIVE.value)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, numOfTabs)
        spinner_num_tabs.adapter = adapter

        btGo.setOnClickListener {
            val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            intent.putExtra("numOfTabs", spinner_num_tabs.selectedItem.toString().toInt())
            startActivity(intent)
        }
    }
}
