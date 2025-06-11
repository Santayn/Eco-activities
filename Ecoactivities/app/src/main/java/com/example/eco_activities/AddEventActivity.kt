package com.example.eco_activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var selectedImagePath: String? = null
    private var selectedImageUri: Uri? = null // Для хранения URI выбранного изображения

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        dbHelper = DbHelper(this, null)

        val titleInput: EditText = findViewById(R.id.event_title)
        val descriptionInput: EditText = findViewById(R.id.event_description)
        val dateInput: EditText = findViewById(R.id.event_date)
        val saveButton: Button = findViewById(R.id.save_event_button)
        val selectImageButton: Button = findViewById(R.id.select_image_button)
        val eventImagePreview: ImageView = findViewById(R.id.event_image_preview)

        val organizerId = intent.getIntExtra("organizerId", -1)
        if (organizerId == -1) {
            Toast.makeText(this, "Ошибка: ID организатора не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        // Обработка нажатия на кнопку "Сохранить"
        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val date = dateInput.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imageName = selectedImagePath ?: "first" // Если изображение не выбрано, используем дефолтное

            val item = Item(
                id = 0,
                image = imageName,
                title = title,
                desc = description,
                text = "",
                price = 0,
                date = date,
                status = "planned",
                organizerId = organizerId
            )

            dbHelper.addItem(item)
            Toast.makeText(this, "Мероприятие добавлено", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Обработка результата выбора изображения
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            val eventImagePreview: ImageView = findViewById(R.id.event_image_preview)
            eventImagePreview.setImageURI(imageUri)

            // Сохраняем URI и получаем путь к изображению
            selectedImageUri = imageUri
            selectedImagePath = getRealPathFromURI(imageUri)
        }
    }

    // Метод для получения реального пути из URI
    private fun getRealPathFromURI(contentUri: Uri?): String? {
        contentUri ?: return null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}