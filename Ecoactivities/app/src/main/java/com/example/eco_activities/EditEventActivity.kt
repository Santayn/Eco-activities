package com.example.eco_activities

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var itemId: Int = -1
    private var organizerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        dbHelper = DbHelper(this, null)

        itemId = intent.getIntExtra("itemId", -1)
        organizerId = intent.getIntExtra("organizerId", -1)

        val titleInput: EditText = findViewById(R.id.event_title)
        val descriptionInput: EditText = findViewById(R.id.event_description)
        val dateInput: EditText = findViewById(R.id.event_date)
        val saveButton: Button = findViewById(R.id.save_event_button)

        val cursor: Cursor? = dbHelper.getItemById(itemId)
        if (cursor != null && cursor.moveToFirst()) {
            titleInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")))
            descriptionInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")))
            dateInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")))
            cursor.close()
        } else {
            Toast.makeText(this, "Мероприятие не найдено", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val date = dateInput.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = Item(
                id = itemId,
                image = "",
                title = title,
                desc = description,
                text = "",
                price = 0,
                date = date,
                status = "planned",
                organizerId = organizerId
            )

            dbHelper.updateItem(item)
            Toast.makeText(this, "Мероприятие обновлено", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}