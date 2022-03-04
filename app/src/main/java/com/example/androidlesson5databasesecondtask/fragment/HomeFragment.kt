package com.example.androidlesson5databasesecondtask.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.adapter.CategoryAdapter
import com.example.androidlesson5databasesecondtask.databinding.FragmentHomeBinding
import com.example.androidlesson5databasesecondtask.databinding.ItemTabLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by viewBinding(CreateMethod.INFLATE)
    lateinit var categoryList: ArrayList<String>
    var categoryAdapter: CategoryAdapter? = null
    var onResumeChecker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        with(binding)
        {


            categoryList = ArrayList(listOf(*resources.getStringArray(R.array.category_array)))
            categoryAdapter = CategoryAdapter(categoryList, requireActivity())
            binding.viewPagerHome.adapter = categoryAdapter

            setHasOptionsMenu(true)
            onResumeChecker = true

            TabLayoutMediator(tabLayout, viewPagerHome) { tab, position ->
            }.attach()
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val itemView =
                        ItemTabLayoutBinding.inflate(LayoutInflater.from(requireContext()),
                            null,
                            false)
                    tab?.customView = null
                    itemView.tabItemLayout.setBackgroundResource(R.drawable.background_tab_layout)
                    itemView.tabItemText.text = categoryList[tab!!.position]
                    itemView.tabItemText.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.darkBlue))
                    tab.customView = itemView.root

                }

                @SuppressLint("ResourceAsColor")
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val itemView =
                        ItemTabLayoutBinding.inflate(LayoutInflater.from(requireContext()),
                            null,
                            false)
                    tab?.customView = null
                    itemView.tabItemLayout.setBackgroundResource(R.drawable.background_tab_layout_empty)
                    itemView.tabItemText.text = categoryList[tab!!.position]
                    itemView.tabItemText.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.white))
                    tab.customView = itemView.root

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
            setTab()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        if (onResumeChecker) {
            categoryAdapter = CategoryAdapter(categoryList, requireActivity())
            binding.viewPagerHome.adapter = categoryAdapter
        }

        onResumeChecker = true
    }


    override fun onPause() {
        super.onPause()

        onResumeChecker = false

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_road_sign, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_road_sign -> {
                findNavController().navigate(R.id.addTrafficSignFragment)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun setTab() {
        val tabCount = binding.tabLayout.tabCount
        for (i in 0 until tabCount) {
            val itemTabLayoutBinding =
                ItemTabLayoutBinding.inflate(LayoutInflater.from(requireContext()), null, false)
            val tabAt = binding.tabLayout.getTabAt(i)
            tabAt!!.customView = itemTabLayoutBinding.root
            itemTabLayoutBinding.tabItemText.text = categoryList[i]
            if (i == 0) {
                itemTabLayoutBinding.tabItemLayout.setBackgroundResource(R.drawable.background_tab_layout)
                itemTabLayoutBinding.tabItemText.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.darkBlue))

            } else {
                itemTabLayoutBinding.tabItemLayout.setBackgroundResource(R.drawable.background_tab_layout_empty)
                itemTabLayoutBinding.tabItemText.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.white))
            }

        }
    }

}