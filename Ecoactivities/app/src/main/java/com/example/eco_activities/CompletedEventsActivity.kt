package com.example.eco_activities

import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedEventsActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_events)

        dbHelper = DbHelper(this, null)
        val rv: RecyclerView = findViewById(R.id.completed_events_list)
        rv.layoutManager = LinearLayoutManager(this)

        val list = mutableListOf<Item>()
        dbHelper.getArchivedItems().use { c ->
            while (c.moveToNext()) {
                list += Item(
                    id = c.getInt(c.getColumnIndexOrThrow("id")),
                    image = c.getString(c.getColumnIndexOrThrow("image")),
                    title = c.getString(c.getColumnIndexOrThrow("title")),
                    desc = c.getString(c.getColumnIndexOrThrow("description")),
                    text = c.getString(c.getColumnIndexOrThrow("text")),
                    price = c.getInt(c.getColumnIndexOrThrow("price")),
                    date = c.getString(c.getColumnIndexOrThrow("date")),
                    status = c.getString(c.getColumnIndexOrThrow("status")),
                    organizerId = c.getInt(c.getColumnIndexOrThrow("organizer_id"))
                )
            }
        }

        rv.adapter = ItemsAdapter(
            items = list,
            onItemClick = { _, _ -> /*можно показать детали*/ },
            isOrganizer = { false }
        )
    }
}