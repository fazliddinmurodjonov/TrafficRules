package com.example.androidlesson5databasesecondtask

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.adapter.SpinnerAdapter
import com.example.androidlesson5databasesecondtask.database.Constant
import com.example.androidlesson5databasesecondtask.database.TSDatabase
import com.example.androidlesson5databasesecondtask.databinding.FragmentEditTrafficSignBinding
import com.example.androidlesson5databasesecondtask.models.CheckNotEmpty
import com.example.androidlesson5databasesecondtask.models.TrafficSign
import java.io.File
import java.io.FileOutputStream

class EditTrafficSignFragment : Fragment(R.layout.fragment_edit_traffic_sign) {
    private val binding: FragmentEditTrafficSignBinding by viewBinding(
        FragmentEditTrafficSignBinding::bind)
    lateinit var tsDatabase: TSDatabase
    lateinit var categoryList: ArrayList<String>

    var imagePath: String? = null

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
            val id = arguments?.getInt("id")
            var sign = tsDatabase.getSignById(id!!)
            placeHolderImg.setOnClickListener {
                pickImageFromGalleryNew()
            }
            var position = 0
            for (s in categoryList) {
                if (s == sign.category) {
                    position = categoryList.indexOf(s)
                    break
                }
            }

            typeOfSignSpinner.setSelection(position)
            placeHolderImg.setImageURI(Uri.parse(sign.imagePath))
            signName.setText(sign.name)
            signDescription.setText(sign.definition)
            imagePath = sign.imagePath


            saveButton.setOnClickListener {
                val name = signName.text.toString()
                val definition = signDescription.text.toString()
                val nameBol = CheckNotEmpty.empty(name)
                val descriptionBol = CheckNotEmpty.empty(definition)
                val spinnerPosition = typeOfSignSpinner.selectedItemPosition
                val category = categoryList[spinnerPosition]
                if (spinnerPosition != 0 && name != " " && definition != " " && nameBol && descriptionBol && imagePath != null) {
                    sign.id = id
                    sign.name = name
                    sign.definition = definition
                    sign.imagePath = imagePath
                    sign.category = category
                    tsDatabase.updateSign(sign)
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