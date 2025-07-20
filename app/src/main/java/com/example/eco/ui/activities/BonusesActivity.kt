package com.example.eco.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.NavigationManager
import com.example.eco.R

import com.example.eco.adapter.BonusesAdapter
import com.example.eco.api.ApiClient
import com.example.eco.viewmodel.BonusesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class BonusesActivity : AppCompatActivity() {

    private lateinit var viewModel: BonusesViewModel
    private lateinit var adapter: BonusesAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonuses)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Инициализация NavigationManager
        NavigationManager(bottomNav, this)

        // Инициализация ApiClient с контекстом
        ApiClient.init(this)



        val recyclerView = findViewById<RecyclerView>(R.id.recycler_operations)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val emptyState = findViewById<TextView>(R.id.empty_state)
        val errorMessage = findViewById<TextView>(R.id.error_message)
        val btnLoadMore = findViewById<Button>(R.id.btn_load_more)
        val editStartDate = findViewById<EditText>(R.id.edit_start_date)
        val editEndDate = findViewById<EditText>(R.id.edit_end_date)

        adapter = BonusesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(BonusesViewModel::class.java)

        // Подписка на данные
        viewModel.bonusHistory.observe(this) { response ->
            if (response != null) {
                progressBar.visibility = View.GONE

                val nonNullContent = response.content?.filterNotNull() ?: emptyList()

                if (nonNullContent.isEmpty()) {
                    emptyState.visibility = View.VISIBLE
                    btnLoadMore.visibility = View.GONE
                } else {
                    emptyState.visibility = View.GONE
                    if (currentPage == 0) {
                        adapter.setBonuses(nonNullContent)
                    } else {
                        adapter.addBonuses(nonNullContent)
                    }
                }

                // Пагинация
                if (response.number < response.totalPages - 1) {
                    btnLoadMore.visibility = View.VISIBLE
                    btnLoadMore.setOnClickListener {
                        currentPage++
                        val startDate = editStartDate.text.toString()
                        val endDate = editEndDate.text.toString()
                        viewModel.loadBonuses(16, currentPage, 10, startDate, endDate)
                    }
                } else {
                    btnLoadMore.visibility = View.GONE
                }

            } else {
                // Можно показать ошибку или просто пустой экран
                progressBar.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            }
        }

        // Подписка на ошибки
        viewModel.error.observe(this) { error ->
            if (error != null) {
                errorMessage.text = error
                errorMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

        // Первая загрузка
        viewModel.loadBonuses(userId = 16, page = 0, size = 10, "", "")
    }
}