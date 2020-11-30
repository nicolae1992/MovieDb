package com.orange.test.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orange.test.ui.fragment.PopularMoviesFragment
import com.orange.test.ui.fragment.TopMoviesFragment

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PopularMoviesFragment()
            }
            1 -> {
                TopMoviesFragment()
            }
            else -> TopMoviesFragment()
        }
    }


}