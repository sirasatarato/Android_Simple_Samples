package com.silso.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private fun moveToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun moveToMainWithHandler() {
        val SPLASH_DISPLAY_LENGTH = 1000L

        Handler().postDelayed({
            moveToMain()
        }, SPLASH_DISPLAY_LENGTH)
    }
}