package com.example.eco_activities

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Находим элементы интерфейса
        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_pass_auth)
        val button: Button = findViewById(R.id.button_reg_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        // Переход на экран регистрации
        linkToReg.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Обработка нажатия на кнопку авторизации
        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val db = DbHelper(this, null)
            val cursor: Cursor? = db.getUserWithRole(login, pass)

            if (cursor != null && cursor.moveToFirst()) {
                val role = cursor.getString(cursor.getColumnIndexOrThrow("role"))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))

                Toast.makeText(this, "Пользователь $login авторизован как $role", Toast.LENGTH_LONG).show()

                val intent = when (role) {
                    "user" -> Intent(this, ItemsActivity::class.java)
                    "organizer" -> {
                        val organizerIntent = Intent(this, OrganizerActivity::class.java)
                        organizerIntent.putExtra("organizerId", userId)
                        organizerIntent
                    }
                    else -> Intent(this, ItemsActivity::class.java)
                }
                startActivity(intent)
                cursor.close()
            } else {
                Toast.makeText(this, "Пользователь $login не авторизован", Toast.LENGTH_LONG).show()
            }
        }
    }
}