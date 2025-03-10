package com.codetarian.bacaquran.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragments: MutableList<Fragment> = ArrayList()

    fun createFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun createFragment(fragment: List<Fragment>) {
        fragments.addAll(fragment)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}