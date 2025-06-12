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

        val userLogin : EditText = findViewById(R.id.user_login_auth)
        val userPass  : EditText = findViewById(R.id.user_pass_auth)
        val btnLogin  : Button   = findViewById(R.id.button_reg_auth)
        val linkReg   : TextView = findViewById(R.id.link_to_reg)

        // Перейти на регистрацию
        linkReg.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass  = userPass.text.toString().trim()
            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db: DbHelper = DbHelper(this, null)
            val cursor: Cursor = db.getUserWithRole(login, pass)
            if (cursor.moveToFirst()) {
                val role   = cursor.getString(cursor.getColumnIndexOrThrow("role"))
                val userId = cursor.getInt   (cursor.getColumnIndexOrThrow("id"))
                cursor.close()

                Toast.makeText(this,
                    "Авторизация прошла (role = $role)", Toast.LENGTH_SHORT
                ).show()

                // Запускаем нужный экран
                val next = when (role) {
                    "organizer" -> Intent(this, OrganizerActivity::class.java)
                        .putExtra("organizerId", userId)
                    else        -> Intent(this, UserProfileActivity::class.java)
                        .putExtra("userId", userId)
                }
                startActivity(next)
                finish()  // чтобы по «Назад» не вернуться сюда
            } else {
                cursor.close()
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}