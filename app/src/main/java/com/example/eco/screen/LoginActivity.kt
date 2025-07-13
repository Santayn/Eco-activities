package com.example.eco.screen
import com.example.eco.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.eco.RegisterActivity

import com.example.eco.screen.MainActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Получаем ссылки на элементы
        val loginEditText = findViewById<EditText>(R.id.login_edittext)
        val passwordEditText = findViewById<EditText>(R.id.password_edittext)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerTextView = findViewById<TextView>(R.id.register_textview)

        // Обработка нажатия кнопки "Войти"
        loginButton.setOnClickListener {
            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Здесь можно добавить логику аутентификации
            if (login.isNotEmpty() && password.isNotEmpty()) {
                // Например, перейти на главный экран
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Показать ошибку
                // TODO: Добавить сообщение об ошибке
            }
        }

        // Обработка нажатия на "Создать аккаунт"
        registerTextView.setOnClickListener {
            // Перейти на экран регистрации
            startActivity(Intent(this, RegisterActivity::class.java))

        }
    }
}