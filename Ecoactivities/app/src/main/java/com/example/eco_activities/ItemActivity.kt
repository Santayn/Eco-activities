package com.example.eco_activities

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        // Инициализация DbHelper
        dbHelper = DbHelper(this, null)

        // Получаем ID мероприятия из Intent
        val itemId = intent.getIntExtra("itemId", -1)
        if (itemId == -1) {
            Toast.makeText(this, "Ошибка: ID мероприятия не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Находим элементы интерфейса
        val title: TextView = findViewById(R.id.item_list_title_one)
        val text: TextView = findViewById(R.id.item_list_text)
        val signUpButton: Button = findViewById(R.id.button_sign)

        // Загружаем данные мероприятия
        val cursor: Cursor? = dbHelper.getItemById(itemId)
        if (cursor != null && cursor.moveToFirst()) {
            title.text = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            text.text = cursor.getString(cursor.getColumnIndexOrThrow("text"))
            cursor.close()
        } else {
            Toast.makeText(this, "Мероприятие не найдено", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Обработка нажатия на кнопку "Записаться"
        signUpButton.setOnClickListener {
            Toast.makeText(this, "Вы успешно записались на мероприятие!", Toast.LENGTH_SHORT).show()
        }
    }
}