package com.example.eco_activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим элементы интерфейса
        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)
        val roleGroup: RadioGroup = findViewById(R.id.role_group)

        // Переход на экран авторизации
        linkToAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        // Обработка нажатия на кнопку регистрации
        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedRoleId = roleGroup.checkedRadioButtonId
            val role = when (selectedRoleId) {
                R.id.radio_user -> "user"
                R.id.radio_organizer -> "organizer"
                else -> "user"
            }

            val user = User(login, email, pass, role)
            val db = DbHelper(this, null)
            db.addUser(user)

            Toast.makeText(this, "Пользователь $login добавлен как $role", Toast.LENGTH_LONG).show()
            userLogin.text.clear()
            userEmail.text.clear()
            userPass.text.clear()
        }
    }
}