package com.example.androidlesson5databasesecondtask.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidlesson5databasesecondtask.fragment.ViewPagerFragment

class CategoryAdapter(val trafficSignList: ArrayList<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return trafficSignList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment.newInstance(trafficSignList[position])
    }

}