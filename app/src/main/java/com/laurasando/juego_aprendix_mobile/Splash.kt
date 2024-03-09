package com.laurasando.juego_aprendix_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.laurasando.juego_aprendix_mobile.databinding.ActivitySplashBinding



class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val duracion = 3000L;


    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        screenSplash.setKeepOnScreenCondition { true }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}