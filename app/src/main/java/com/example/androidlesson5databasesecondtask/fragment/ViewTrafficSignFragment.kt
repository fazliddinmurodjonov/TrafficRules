package com.example.androidlesson5databasesecondtask.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.database.TSDatabase
import com.example.androidlesson5databasesecondtask.databinding.FragmentViewTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.TrafficSign
import java.net.URI


class ViewTrafficSignFragment : Fragment(R.layout.fragment_view_traffic_sign) {

    private val binding by viewBinding(FragmentViewTrafficSignBinding::bind)
    lateinit var tsDatabase: TSDatabase
    lateinit var sign: TrafficSign
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        with(binding)
        {
            val id = arguments?.getInt("id")
            tsDatabase = TSDatabase(requireContext())
            sign = tsDatabase.getSignById(id!!)
            signImg.setImageURI(Uri.parse(sign.imagePath))
            signName.text = sign.name
            signDescription.text = sign.definition
        }
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setTitle("${sign.name}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }


}