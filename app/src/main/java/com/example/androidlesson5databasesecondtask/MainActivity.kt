package com.example.androidlesson5databasesecondtask

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.MultiplePermissionsReport

import com.karumi.dexter.listener.multi.MultiplePermissionsListener

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewBinding: ActivityMainBinding by viewBinding()
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding)
        {
            Dexter.withContext(this@MainActivity)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest?>?,
                        token: PermissionToken?,
                    ) {

                    }
                }).check()
            setSupportActionBar(toolbar)
            toolbar.elevation = 0F
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration =
                AppBarConfiguration(setOf(R.id.nav_home,
                    R.id.nav_love,
                    R.id.nav_about, R.id.addTrafficSignFragment, R.id.viewTrafficSignFragment,R.id.editTrafficSignFragment))
            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                bottomNavigationView.isVisible = destination.id !in destinationsWithoutBottomNav
            }
        }

    }

    private val destinationsWithoutBottomNav = listOf(
        R.id.addTrafficSignFragment, R.id.viewTrafficSignFragment, R.id.editTrafficSignFragment
    )

}