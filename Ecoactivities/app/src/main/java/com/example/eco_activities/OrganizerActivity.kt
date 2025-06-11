package com.example.eco_activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrganizerActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var organizerId: Int = -1
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer)

        organizerId = intent.getIntExtra("organizerId", -1)
        if (organizerId < 0) finish()

        dbHelper = DbHelper(this, null)

        // 1) RecyclerView
        recyclerView = findViewById(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 2) Кнопка "Добавить"
        findViewById<Button>(R.id.add_event_button).setOnClickListener {
            startActivity(
                Intent(this, AddEventActivity::class.java)
                    .putExtra("organizerId", organizerId)
            )
        }

        // 3) Кнопка "Архив"
        findViewById<Button>(R.id.archive_button).setOnClickListener {
            startActivity(
                Intent(this, CompletedEventsActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        // каждый раз при возврате на экран заново подгружаем запланированные
        loadPlannedEvents()
    }

    private fun loadPlannedEvents() {
        val list = mutableListOf<Item>()
        dbHelper.getPlannedItemsByOrganizer(organizerId).use { cursor ->
            while (cursor.moveToNext()) {
                list += Item(
                    id          = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    image       = cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    title       = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    desc        = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    text        = cursor.getString(cursor.getColumnIndexOrThrow("text")),
                    price       = cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                    date        = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                    status      = cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    organizerId = organizerId
                )
            }
        }

        recyclerView.adapter = ItemsAdapter(
            items = list,
            onItemClick = { itemId: Int, action: String ->
                when (action) {
                    "delete" -> {
                        dbHelper.deleteItem(itemId)
                        loadPlannedEvents()
                    }
                    "complete" -> {
                        dbHelper.completeItem(itemId)
                        loadPlannedEvents() // после complete оно уже не попадает в planned
                    }
                }
            },
            isOrganizer = { true }
        )
    }
}