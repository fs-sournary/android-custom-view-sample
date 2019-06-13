package com.sournary.chartviewsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Past week")
        menu.add("Past month")
        menu.add("Past year")
        menu.add("All data")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Past week" -> {
                chart.showLast(7)
            }
            "Past month" -> {
                chart.showLast(30)
            }
            "Past year" -> {
                chart.showLast(365)
            }
            "All data" -> {
                chart.showLast()
            }
        }
        return true
    }
}
