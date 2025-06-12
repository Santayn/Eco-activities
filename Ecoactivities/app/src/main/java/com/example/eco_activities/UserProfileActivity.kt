package com.example.eco_activities

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserProfileActivity : AppCompatActivity() {

    private lateinit var dbHelper : DbHelper
    private var    userId   : Int  = -1

    private lateinit var btnAllEvents: Button
    private lateinit var btnActive: Button
    private lateinit var btnArchive: Button
    private lateinit var rvEvents: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Получаем userId
        userId = intent.getIntExtra("userId", -1)
        if (userId < 0) finish()

        // Инициализация
        dbHelper     = DbHelper(this, null)
        btnAllEvents = findViewById(R.id.btn_all_events)
        btnActive    = findViewById(R.id.btn_active)
        btnArchive   = findViewById(R.id.btn_archive)
        rvEvents     = findViewById(R.id.rv_profile_events)
        rvEvents.layoutManager = LinearLayoutManager(this)

        // Переход в общий список
        btnAllEvents.setOnClickListener {
            startActivity(
                Intent(this, ItemsActivity::class.java)
                    .putExtra("userId", userId)
            )
        }

        // Переключатели «Активные» / «Архив»
        btnActive.setOnClickListener  { showActive() }
        btnArchive.setOnClickListener { showArchive() }

        // По умолчанию – активные
        showActive()
    }

    private fun showActive() {
        val cursor: Cursor = dbHelper.getUserSignedActiveEvents(userId)
        val list = mutableListOf<Item>()
        cursor.use {
            while (it.moveToNext()) {
                val id    = it.getInt(it.getColumnIndexOrThrow("id"))
                val count = dbHelper.getSignUpCount(id)
                list += Item(
                    id          = id,
                    image       = it.getString(it.getColumnIndexOrThrow("image")),
                    title       = it.getString(it.getColumnIndexOrThrow("title")),
                    desc        = it.getString(it.getColumnIndexOrThrow("description")),
                    text        = it.getString(it.getColumnIndexOrThrow("text")),
                    price       = 0,
                    date        = it.getString(it.getColumnIndexOrThrow("date")),
                    status      = it.getString(it.getColumnIndexOrThrow("status")),
                    organizerId = it.getInt(it.getColumnIndexOrThrow("organizer_id")),
                    signUpCount = count
                )
            }
        }
        rvEvents.adapter = ItemsAdapter(list, { _, _ -> }, { false })
    }

    private fun showArchive() {
        val cursor: Cursor = dbHelper.getUserSignedArchivedEvents(userId)
        val list = mutableListOf<Item>()
        cursor.use {
            while (it.moveToNext()) {
                val id    = it.getInt(it.getColumnIndexOrThrow("id"))
                val count = dbHelper.getSignUpCount(id)
                list += Item(
                    id          = id,
                    image       = it.getString(it.getColumnIndexOrThrow("image")),
                    title       = it.getString(it.getColumnIndexOrThrow("title")),
                    desc        = it.getString(it.getColumnIndexOrThrow("description")),
                    text        = it.getString(it.getColumnIndexOrThrow("text")),
                    price       = 0,
                    date        = it.getString(it.getColumnIndexOrThrow("date")),
                    status      = it.getString(it.getColumnIndexOrThrow("status")),
                    organizerId = it.getInt(it.getColumnIndexOrThrow("organizer_id")),
                    signUpCount = count
                )
            }
        }
        rvEvents.adapter = ItemsAdapter(list, { _, _ -> }, { false })
    }
}