package com.example.eco
import RegistrationPagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.example.eco.R
import com.example.eco.screen.LoginActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Получаем ссылки на элементы
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)

        // Устанавливаем адаптер для ViewPager
        viewPager.adapter = RegistrationPagerAdapter(supportFragmentManager)

        // Соединяем TabLayout с ViewPager
        tabLayout.setupWithViewPager(viewPager)
    }

    // Обработка нажатия на "Уже есть аккаунт? Войти"
    fun onLoginClick(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}