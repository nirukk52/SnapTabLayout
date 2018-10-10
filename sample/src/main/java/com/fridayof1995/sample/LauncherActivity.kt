package com.fridayof1995.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_launcher.*


class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val arraySpinner = arrayOf("3", "5")
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner)
        spinner_num_tabs.adapter = adapter

        btGo.setOnClickListener {
            val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            intent.putExtra("expandedAt", etExpandsAt.text.toString().toInt())
            intent.putExtra("numOfTabs", spinner_num_tabs.selectedItem.toString().toInt())
            startActivity(intent)
        }

    }
}
