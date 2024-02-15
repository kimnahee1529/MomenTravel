package com.android.traveltube.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.traveltube.R
import com.android.traveltube.databinding.ActivityMainBinding
import com.android.traveltube.utils.Constants.COUNTRY_KEY
import com.android.traveltube.utils.Constants.FAVORITES_KEY

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setUpJetpackNavigation()

        initStartFragment()
    }

    private fun initStartFragment() {
        sharedPref = getSharedPreferences("preferenceName", Context.MODE_PRIVATE)
        val country = sharedPref.getString(COUNTRY_KEY,"")

        if (country.isNullOrBlank()) {
            navController.navigate(R.id.fragment_country)
        } else {
            navController.navigate(R.id.fragment_home)
        }
    }

    private fun setUpJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationVisibility(destination.id)
        }
    }

    private fun bottomNavigationVisibility(destinationId: Int) {
        val isVideoDetailFragment = destinationId == R.id.fragment_video_detail ||
                destinationId == R.id.fragment_detail_city ||
                destinationId == R.id.fragment_shorts ||
                destinationId == R.id.fragment_country

        binding.bottomNavigationView.visibility = if (isVideoDetailFragment) View.GONE else View.VISIBLE
    }
}