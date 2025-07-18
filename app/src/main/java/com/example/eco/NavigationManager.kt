package com.example.eco

import android.content.Context
import android.content.Intent
import com.example.eco.ui.activities.EventsActivity
import com.example.eco.ui.activities.OrganizationProfileActivity
import com.example.eco.ui.activities.BonusesActivity // ← Добавьте импорт вашей активити
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationManager(
    bottomNav: BottomNavigationView,
    context: Context
) {

    private val bottomNav: BottomNavigationView = bottomNav
    private val context: Context = context

    init {
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_events -> {
                    context.startActivity(Intent(context, EventsActivity::class.java))
                }
                R.id.nav_profile -> {
                    context.startActivity(Intent(context, OrganizationProfileActivity::class.java))
                }
                R.id.nav_bonuses -> {
                    context.startActivity(Intent(context, BonusesActivity::class.java)) // ← Здесь ваш экран бонусов
                }
            }
            true
        }
    }
}