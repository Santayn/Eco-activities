// File: MainActivity.kt

package com.example.eco.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.eco.ui.screen.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Перейти к LoginActivity и закрыть MainActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}