package com.android.traveltube.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.traveltube.R
import com.android.traveltube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setUpJetpackNavigation()
    }

    private fun setUpJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationVisibility(destination.id)
        }
    }

    private fun bottomNavigationVisibility(destinationId: Int) {
        val isVideoDetailFragment = destinationId == R.id.fragment_video_detail
        binding.bottomNavigationView.visibility = if (isVideoDetailFragment) View.GONE else View.VISIBLE
    }

}