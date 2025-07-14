package com.example.eco.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eco.R
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.auth.AuthRequestDto
import com.example.eco.api.dto.auth.AuthResponseDto
import com.example.eco.ui.activities.EventsActivity
import com.example.eco.ui.activities.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginEditText = findViewById<EditText>(R.id.login_edittext)
        val passwordEditText = findViewById<EditText>(R.id.password_edittext)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerTextView = findViewById<TextView>(R.id.register_textview)

        loginButton.setOnClickListener {
            val login = loginEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = AuthRequestDto(login, password)
            ApiClient.authService.login(request).enqueue(object : Callback<AuthResponseDto> {
                override fun onResponse(call: Call<AuthResponseDto>, response: Response<AuthResponseDto>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            Toast.makeText(this@LoginActivity, "Добро пожаловать, ${user.fullName}!", Toast.LENGTH_SHORT).show()
                            // Перейти к главной активности
                            startActivity(Intent(this@LoginActivity, EventsActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Ошибка сервера: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponseDto>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Ошибка подключения: ${t.message}", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            })
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}