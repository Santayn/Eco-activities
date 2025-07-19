package com.example.eco.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eco.NavigationManager
import com.example.eco.R
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.user.UserCollapseDTO
import com.example.eco.ui.screen.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizationProfileActivity : AppCompatActivity() {

    // === UI элементы ===
    private lateinit var tv_full_name: TextView
    private lateinit var tv_organization_type: TextView
    private lateinit var tv_events_created: TextView
    private lateinit var tv_total_participants: TextView
    private lateinit var btn_logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_profile)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationManager(bottomNav, this)

        // === Инициализация UI ===
        tv_full_name = findViewById(R.id.tv_full_name)
        tv_organization_type = findViewById(R.id.tv_organization_type)
        tv_events_created = findViewById(R.id.tv_events_created)
        tv_total_participants = findViewById(R.id.tv_total_participants)
        btn_logout = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener {
            logoutUser()
        }

        // === Загрузка данных профиля ===
        loadUserProfile()
    }

    private fun loadUserProfile() {
        ApiClient.userService.getCurrentUserProfile().enqueue(object :
            Callback<UserCollapseDTO> {
            override fun onResponse(
                call: Call<UserCollapseDTO>,
                response: Response<UserCollapseDTO>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    if (profile != null) {
                        tv_full_name.text = profile.fullName
                        tv_organization_type.text = profile.role
                        // Предположим, что registeredEventsCount и totalBonusPoints теперь хранятся в других полях
                        tv_events_created.text = "0" // Здесь можно добавить логику для новых полей
                        tv_total_participants.text = "0" // Здесь можно добавить логику для новых полей
                    }
                } else {
                    // Можно показать Toast или сообщение об ошибке
                }
            }

            override fun onFailure(call: Call<UserCollapseDTO>, t: Throwable) {
                // TODO: Обработка ошибки загрузки
            }
        })
    }

    private fun logoutUser() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}