package com.example.androidlesson5databasesecondtask.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.adapter.TrafficSignAdapter
import com.example.androidlesson5databasesecondtask.database.Constant
import com.example.androidlesson5databasesecondtask.database.TSDatabase
import com.example.androidlesson5databasesecondtask.databinding.FragmentLoveBinding
import com.example.androidlesson5databasesecondtask.databinding.ItemTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.TrafficSign


class LoveFragment : Fragment(R.layout.fragment_love) {
    private val binding by viewBinding(FragmentLoveBinding::bind)
    lateinit var trafficSignAdapter: TrafficSignAdapter
    lateinit var tsDatabase: TSDatabase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding)
        {
            val query = "select *from ${Constant.TABLE} WHERE ${Constant.LIKE} = 1"
            tsDatabase = TSDatabase(requireContext())
            val likedSignList = tsDatabase.getAllSigns(query)
            trafficSignAdapter = TrafficSignAdapter(likedSignList)
            likedSignsRV.adapter = trafficSignAdapter
            trafficSignAdapter = TrafficSignAdapter(likedSignList)
            likedSignsRV.adapter = trafficSignAdapter
            trafficSignAdapter.notifyItemRemoved(1)
            trafficSignAdapter
            trafficSignAdapter.setOnItemClick(object : TrafficSignAdapter.OnItemClick {
                override fun onClick(trafficSign: TrafficSign, position: Int) {
                    val bundleOf = bundleOf("id" to trafficSign.id)
                    findNavController().navigate(R.id.viewTrafficSignFragment, bundleOf)
                }

            })
            trafficSignAdapter.setOnItemLiked(object : TrafficSignAdapter.OnItemLiked {
                @SuppressLint("NotifyDataSetChanged")
                override fun onClick(
                    trafficSign: TrafficSign,
                    binding: ItemTrafficSignBinding,
                    position: Int,
                ) {
                    if (trafficSign.like == 1) {
                        binding.likedSign.setImageResource(R.drawable.love_empty)
                        likedSignList[position].like = 0
                    } else {
                        binding.likedSign.setImageResource(R.drawable.love)
                        likedSignList[position].like = 1
                    }

                    likedSignList.remove(trafficSign)
                    trafficSignAdapter.notifyItemRemoved(position)
                    trafficSignAdapter.notifyItemChanged(position, likedSignList.size)
                    tsDatabase.updateSign(trafficSign)
                }
            })

            trafficSignAdapter.setOnItemDelete(object : TrafficSignAdapter.OnItemDelete {
                override fun onClick(trafficSign: TrafficSign, position: Int) {
                    tsDatabase.deleteSign(trafficSign)
                    likedSignList.remove(trafficSign)
                    trafficSignAdapter.notifyItemRemoved(position)
                    trafficSignAdapter.notifyItemChanged(position, likedSignList.size)
                }

            })
            trafficSignAdapter.setOnItemEdit(object : TrafficSignAdapter.OnItemEdit {
                override fun onClick(trafficSign: TrafficSign, position: Int) {
                    val bundleOf = bundleOf("id" to trafficSign.id)
                    findNavController().navigate(R.id.editTrafficSignFragment, bundleOf)
                }

            })

        }
    }

}