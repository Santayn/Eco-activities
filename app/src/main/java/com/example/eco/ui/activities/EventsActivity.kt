package com.example.eco.ui.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.EventAdapter
import com.example.eco.R
import com.example.eco.ui.screen.EventsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(emptyList())
        recyclerView.adapter = adapter

        // Подписка на изменения через корутину
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.events.collect { events ->
                adapter = EventAdapter(events)
                recyclerView.adapter = adapter
            }
        }
    }
}