package com.example.eco.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.eco.R
import com.example.eco.ui.adapter.RegistrationPagerAdapter
import com.example.eco.ui.screen.LoginActivity

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val adapter = RegistrationPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "ВОЛОНТЕР"
                1 -> "ОРГАНИЗАЦИЯ"
                else -> null
            }
        }.attach()
    }

    fun onLoginClick(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}