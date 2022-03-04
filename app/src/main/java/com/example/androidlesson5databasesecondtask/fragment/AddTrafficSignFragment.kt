package com.example.androidlesson5databasesecondtask.fragment

import android.app.ActionBar
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.R
import com.example.androidlesson5databasesecondtask.adapter.SpinnerAdapter
import com.example.androidlesson5databasesecondtask.database.Constant
import com.example.androidlesson5databasesecondtask.database.TSDatabase
import com.example.androidlesson5databasesecondtask.databinding.FragmentAddTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.CheckNotEmpty
import com.example.androidlesson5databasesecondtask.models.TrafficSign
import java.io.File
import java.io.FileOutputStream


class AddTrafficSignFragment : Fragment(R.layout.fragment_add_traffic_sign) {
    private val binding: FragmentAddTrafficSignBinding by viewBinding(FragmentAddTrafficSignBinding::bind)
    lateinit var tsDatabase: TSDatabase
    var imagePath: String? = null
    var imageName: String? = null
    lateinit var categoryList: ArrayList<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        with(binding)
        {
            tsDatabase = TSDatabase(requireContext())
            categoryList = ArrayList<String>()
            categoryList.add("Type")

            categoryList.addAll(ArrayList(listOf(*resources.getStringArray(R.array.category_array))))
            val spinnerAdapter = SpinnerAdapter(categoryList)
            typeOfSignSpinner.adapter = spinnerAdapter
            placeHolderImg.setOnClickListener {
                pickImageFromGalleryNew()
            }
            saveButton.setOnClickListener {

                val name = signName.text.toString()
                val definition = signDescription.text.toString()
                val nameBol = CheckNotEmpty.empty(name)
                val descriptionBol = CheckNotEmpty.empty(definition)
                val spinnerPosition = typeOfSignSpinner.selectedItemPosition
                val category = categoryList[spinnerPosition]

                if (spinnerPosition != 0 && name != " " && definition != " " && nameBol && descriptionBol && imagePath != null) {
                    val trafficSign = TrafficSign(name, definition, imagePath, category, 0)
                    tsDatabase.insertSign(trafficSign)
                    findNavController().popBackStack()
                }
            }


        }


    }


    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri ?: return@registerForActivityResult
            binding.placeHolderImg.setImageURI(uri)
            val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
            val query = "select * from ${Constant.TABLE}"
            var numberText = "1234567890"
            val toCharArray = numberText.toCharArray()
            toCharArray.shuffle()

            var imageName: String = ""
            for (c in toCharArray) {
                imageName += c
            }
            val file = File(requireActivity().filesDir, "$imageName.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
            fileOutputStream.close()
            val absolutePath = file.absolutePath
            imagePath = absolutePath
        }


    private fun pickImageFromGalleryNew() {
        getImageContent.launch("image/*")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }


}