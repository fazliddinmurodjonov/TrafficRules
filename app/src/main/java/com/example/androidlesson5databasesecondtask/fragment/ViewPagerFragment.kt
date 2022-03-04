package com.example.androidlesson5databasesecondtask.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.adapter.CategoryAdapter
import com.example.androidlesson5databasesecondtask.adapter.TrafficSignAdapter
import com.example.androidlesson5databasesecondtask.database.Constant
import com.example.androidlesson5databasesecondtask.database.TSDatabase
import com.example.androidlesson5databasesecondtask.databinding.FragmentViewPagerBinding
import com.example.androidlesson5databasesecondtask.databinding.ItemTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.TrafficSign

private const val TRAFFIC_SIGN = "sign"
private var trafficSignCategory: String? = null

class ViewPagerFragment : Fragment() {

    private val binding: FragmentViewPagerBinding by viewBinding(CreateMethod.INFLATE)
    lateinit var tsDatabase: TSDatabase
    lateinit var query: String
    lateinit var trafficSignList: ArrayList<TrafficSign>
    lateinit var trafficSignAdapter: TrafficSignAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        with(binding)
        {
            trafficSignList = ArrayList()
            trafficSignCategory = arguments?.getString(TRAFFIC_SIGN)
            tsDatabase = TSDatabase(requireContext())
            query =
                "select * from ${Constant.TABLE} WHERE ${Constant.CATEGORY}  = '${trafficSignCategory}'"
            trafficSignList = tsDatabase.getAllSigns(query)

            trafficSignAdapter = TrafficSignAdapter(trafficSignList)
            signsRV.adapter = trafficSignAdapter
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
                        trafficSignList[position].like = 0
                    } else {
                        binding.likedSign.setImageResource(R.drawable.love)
                        trafficSignList[position].like = 1
                    }

                    trafficSignAdapter.notifyItemChanged(position)
                    trafficSignAdapter = TrafficSignAdapter(trafficSignList)
                    tsDatabase.updateSign(trafficSign)
                }
            })

            trafficSignAdapter.setOnItemDelete(object : TrafficSignAdapter.OnItemDelete {
                override fun onClick(trafficSign: TrafficSign, position: Int) {
                    tsDatabase.deleteSign(trafficSign)
                    trafficSignList.remove(trafficSign)
                    trafficSignAdapter.notifyItemRemoved(position)
                    trafficSignAdapter.notifyItemChanged(position, trafficSignList.size)
                }

            })
            trafficSignAdapter.setOnItemEdit(object : TrafficSignAdapter.OnItemEdit {
                override fun onClick(trafficSign: TrafficSign, position: Int) {
                    val bundleOf = bundleOf("id" to trafficSign.id)
                    findNavController().navigate(R.id.editTrafficSignFragment, bundleOf)
                }

            })
        }

        return binding.root
    }



    companion object {

        @JvmStatic
        fun newInstance(trafficSign: String) =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(TRAFFIC_SIGN, trafficSign)
                }
            }
    }
}