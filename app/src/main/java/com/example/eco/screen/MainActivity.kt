package com.example.eco.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Перейти к LoginActivity и закрыть MainActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}