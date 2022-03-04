package com.example.androidlesson5databasesecondtask.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.databinding.ItemTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.TrafficSign

class TrafficSignAdapter(var signList: ArrayList<TrafficSign>) :
    RecyclerView.Adapter<TrafficSignAdapter.TrafficSignHolder>() {
    lateinit var adapterEdit: OnItemEdit
    lateinit var adapterDelete: OnItemDelete
    lateinit var adapterLiked: OnItemLiked
    lateinit var adapterClick: OnItemClick

    interface OnItemClick {
        fun onClick(trafficSign: TrafficSign, position: Int)

    }

    interface OnItemEdit {
        fun onClick(trafficSign: TrafficSign, position: Int)
    }

    interface OnItemDelete {
        fun onClick(trafficSign: TrafficSign, position: Int)

    }

    interface OnItemLiked {
        fun onClick(trafficSign: TrafficSign, binding: ItemTrafficSignBinding, position: Int)
    }

    fun setOnItemClick(itemClick: OnItemClick) {
        adapterClick = itemClick
    }

    fun setOnItemEdit(itemEdit: OnItemEdit) {
        adapterEdit = itemEdit
    }

    fun setOnItemDelete(itemDelete: OnItemDelete) {
        adapterDelete = itemDelete
    }

    fun setOnItemLiked(itemLiked: OnItemLiked) {
        adapterLiked = itemLiked
    }

    inner class TrafficSignHolder(var binding: ItemTrafficSignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(trafficSign: TrafficSign, binding: ItemTrafficSignBinding, position: Int) {
            binding.trafficSignName.text = trafficSign.name
            binding.trafficSignImg.setImageURI(Uri.parse(trafficSign.imagePath))
            if (trafficSign.like == 1) {
                binding.likedSign.setImageResource(R.drawable.love)
            }
            binding.root.setOnClickListener {
                adapterClick.onClick(trafficSign, position)
            }
            binding.likedSign.setOnClickListener {
                adapterLiked.onClick(trafficSign, binding, position)
            }
            binding.editButton.setOnClickListener {
                adapterEdit.onClick(trafficSign, position)
            }
            binding.deleteButton.setOnClickListener {
                adapterDelete.onClick(trafficSign, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrafficSignHolder {
        return TrafficSignHolder(ItemTrafficSignBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: TrafficSignHolder, position: Int) {
        val trafficSign = signList[position]
        holder.onBind(trafficSign, holder.binding, position)
    }

    override fun getItemCount(): Int {
        return signList.size
    }
}