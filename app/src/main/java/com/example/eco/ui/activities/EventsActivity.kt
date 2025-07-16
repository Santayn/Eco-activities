package com.example.eco.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.EventRepository
import com.example.eco.EventViewModelFactory
import com.example.eco.R
import com.example.eco.api.ApiClient
import com.example.eco.adapter.EventAdapter
import com.example.eco.ui.screen.EventsViewModel
import kotlinx.coroutines.launch

class EventsActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val apiService = ApiClient.eventService
        val repository = EventRepository(apiService)
        viewModel = ViewModelProvider(this, EventViewModelFactory(repository))[EventsViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(mutableListOf())
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.events.collect { events ->
                adapter.submitList(events)
            }
        }
    }
}