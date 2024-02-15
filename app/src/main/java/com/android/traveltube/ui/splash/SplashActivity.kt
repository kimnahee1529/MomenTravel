package com.android.traveltube.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import com.android.traveltube.R
import com.android.traveltube.databinding.ActivitySplashBinding
import com.android.traveltube.main.MainActivity
import java.util.zip.Inflater

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, SPLASH_TIME_OUT)
    }
}