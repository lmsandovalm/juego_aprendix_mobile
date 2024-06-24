package com.laurasando.juego_aprendix_mobile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.databinding.ActivitySplash1Binding
import com.laurasando.juego_aprendix_mobile.navigation.MenuNavegacion
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.io.IOException

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplash1Binding
    private lateinit var sharedPreferences: SharePreferencesManager
    private val duration = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = SharePreferencesManager(this)
        // Load GIF using Glide
        val gifImageView: GifImageView = findViewById(R.id.gifImageView)
        val gifResourceId = R.drawable.gif_splash

        try {
            val gifFromResource = GifDrawable(resources, gifResourceId)
            gifImageView.setImageDrawable(gifFromResource)
        } catch (e: IOException) {
            e.printStackTrace()
        }


        Handler().postDelayed({
            val userActive = sharedPreferences.getPref("session_active", false) as Boolean
            if (userActive) {
                startActivity(Intent(this, MenuNavegacion::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
        }, duration)
    }
}
