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

import com.example.eco.R
import com.example.eco.adapter.BonusesAdapter
import com.example.eco.viewmodel.BonusesViewModel

class BonusesActivity : AppCompatActivity() {

    private lateinit var viewModel: BonusesViewModel
    private lateinit var adapter: BonusesAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonuses)

        // Инициализация RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_operations)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
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
            if (response != null && response.content.isNotEmpty()) {
                if (currentPage == 0) {
                    adapter.setBonuses(response.getNonNullContent())
                } else {
                    adapter.addBonuses(response.getNonNullContent())
                }

                progressBar.visibility = View.GONE
                btnLoadMore.isEnabled = true
                currentPage++

                if (response.content.isEmpty()) {
                    findViewById<TextView>(R.id.empty_state).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.empty_state).visibility = View.GONE
                }

                if (response.number < response.totalPages - 1) {
                    btnLoadMore.visibility = View.VISIBLE
                } else {
                    btnLoadMore.visibility = View.GONE
                }
            }
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                errorMessage.text = error
                errorMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

        // Загрузка данных
        btnLoadMore.setOnClickListener {
            btnLoadMore.isEnabled = false
            progressBar.visibility = View.VISIBLE
            val startDate = editStartDate.text.toString()
            val endDate = editEndDate.text.toString()
            viewModel.loadBonuses(123, currentPage, 10, startDate, endDate)
        }

        // Первая загрузка
        viewModel.loadBonuses(123, 0, 10, "", "")
    }
}