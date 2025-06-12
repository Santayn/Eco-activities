package com.example.eco_activities

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var itemId: Int = -1
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        dbHelper = DbHelper(this, null)

        // 1) Получаем itemId и userId из Intent
        itemId = intent.getIntExtra("itemId", -1)
        userId = intent.getIntExtra("userId", -1)

        if (itemId < 0) {
            Toast.makeText(this, "Ошибка: ID мероприятия не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2) Находим вьюшки
        val titleView: TextView = findViewById(R.id.item_list_title_one)
        val textView : TextView = findViewById(R.id.item_list_text)
        val signUpBtn: Button   = findViewById(R.id.button_sign)

        // 3) Загружаем данные мероприятия
        val cursor: Cursor = dbHelper.getItemById(itemId)
        if (cursor.moveToFirst()) {
            titleView.text = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            textView .text = cursor.getString(cursor.getColumnIndexOrThrow("text"))
        } else {
            Toast.makeText(this, "Мероприятие не найдено", Toast.LENGTH_SHORT).show()
            cursor.close()
            finish()
            return
        }
        cursor.close()

        // 4) Если userId не передан — прячем кнопку
        if (userId < 0) {
            signUpBtn.visibility = View.GONE
            return
        }

        // 5) Проверяем, записан ли уже пользователь
        val already = dbHelper.isUserSigned(userId, itemId)
        if (already) {
            signUpBtn.apply {
                text = "Вы уже записаны"
                isEnabled = false
            }
        }

        // 6) Обработка клика по "Записаться"
        signUpBtn.setOnClickListener {
            if (dbHelper.addSignUp(userId, itemId)) {
                Toast.makeText(this, "Вы успешно записаны!", Toast.LENGTH_SHORT).show()
                // блокируем кнопку, чтобы не кликали повторно
                signUpBtn.apply {
                    text = "Вы уже записаны"
                    isEnabled = false
                }
            } else {
                Toast.makeText(this, "Не удалось записаться (возможно, вы уже здесь)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}