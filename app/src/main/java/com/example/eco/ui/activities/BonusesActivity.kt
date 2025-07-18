    package com.example.eco.ui.activities


    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import com.example.eco.NavigationManager
    import com.example.eco.R
    import com.example.eco.api.ApiClient
    import com.google.android.material.bottomnavigation.BottomNavigationView

    class BonusesActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bonuses) // ← должен существовать
            val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            // Инициализация NavigationManager
            NavigationManager(bottomNav, this)

            // Инициализация ApiClient с контекстом
            ApiClient.init(this)
        }

    }