// File: EventsActivity.kt

package com.example.eco.ui.activities

import EventRepository
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.EventViewModelFactory
import com.example.eco.NavigationManager
import com.example.eco.R
import com.example.eco.api.ApiClient
import com.example.eco.adapter.EventAdapter
import com.example.eco.ui.screen.EventsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Инициализация NavigationManager
        NavigationManager(bottomNav, this)

        // Инициализация ApiClient с контекстом
        ApiClient.init(this)

        val apiService = ApiClient.eventService
        val repository = EventRepository(apiService)
        val viewModel = ViewModelProvider(this, EventViewModelFactory(repository))[EventsViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = EventAdapter()
        recyclerView.adapter = adapter

        // Подписываемся на изменения events через collect
        lifecycleScope.launch {
            viewModel.events.collect { events ->
                adapter.submitList(events)
            }
        }
    }
}