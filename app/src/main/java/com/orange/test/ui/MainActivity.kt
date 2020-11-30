package com.orange.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.orange.test.R
import com.orange.test.ui.adapter.TabsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setTabLayout()
        observable()
    }

    private fun setTabLayout(){
     //   tabLayout?.setSelectedTabIndicatorColor(Color.parseColor(getString(R.color.purple_500)))
        tabLayout?.tabTextColors = ContextCompat.getColorStateList(this, android.R.color.white)

        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle)
        tabViewPager?.adapter = adapter
        tabViewPager.isUserInputEnabled = true
    }

    private fun observable(){
        TabLayoutMediator(tabLayout, tabViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Popular movies"
                }
                1 -> {
                    tab.text = "Top movies"
                }
            }
        }.attach()
    }
}