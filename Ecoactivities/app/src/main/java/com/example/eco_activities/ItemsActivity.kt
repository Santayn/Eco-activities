package com.example.eco_activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        // 1) Читаем userId (если есть)
        userId = intent.getIntExtra("userId", -1)

        // 2) «Личный кабинет»
        findViewById<Button>(R.id.btn_profile).apply {
            if (userId < 0) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                setOnClickListener {
                    startActivity(
                        Intent(this@ItemsActivity, UserProfileActivity::class.java)
                            .putExtra("userId", userId)
                    )
                }
            }
        }

        // 3) БД + авто-архив
        dbHelper = DbHelper(this, null)
        dbHelper.archiveExpiredEvents()

        // 4) Загружаем список
        val rv = findViewById<RecyclerView>(R.id.itemslist).apply {
            layoutManager = LinearLayoutManager(this@ItemsActivity)
        }

        val list = mutableListOf<Item>()
        dbHelper.getActiveItems().use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow("id"))
                list += Item(
                    id,
                    c.getString(c.getColumnIndexOrThrow("image")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("description")),
                    c.getString(c.getColumnIndexOrThrow("text")),
                    c.getInt(c.getColumnIndexOrThrow("price")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("status")),
                    c.getInt(c.getColumnIndexOrThrow("organizer_id"))
                )
            }
        }

        // 5) Навешиваем адаптер
        rv.adapter = ItemsAdapter(
            items = list,
            onItemClick = { itemId, action ->
                when (action) {
                    "open" -> {
                        // Кликаем по «Открыть»
                        startActivity(
                            Intent(this, ItemActivity::class.java).apply {
                                putExtra("itemId", itemId)
                                putExtra("userId", userId)
                            }
                        )
                    }
                    "delete" -> {
                        dbHelper.deleteItem(itemId)
                        recreate()  // или вручную перезагрузить список
                    }
                    "complete" -> {
                        dbHelper.completeItem(itemId)
                        recreate()
                    }
                }
            },
            isOrganizer = { /* здесь вашу логику: true, если userId — организатор */ false }
        )
    }
}