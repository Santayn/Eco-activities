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

        // Инициализация UI
        val recyclerView: RecyclerView = findViewById(R.id.recycler_operations)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val errorMessage: TextView = findViewById(R.id.error_message)
        val btnLoadMore: Button = findViewById(R.id.btn_load_more)
        val editStartDate: EditText = findViewById(R.id.edit_start_date)
        val editEndDate: EditText = findViewById(R.id.edit_end_date)

        // Настройка RecyclerView
        adapter = BonusesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this).get(BonusesViewModel::class.java)

        // Подписка на данные
        viewModel.bonusHistory.observe(this) { response ->
            if (response != null) {
                val nonNullContent = response.getNonNullContent()

                if (currentPage == 0) {
                    adapter.setBonuses(nonNullContent)
                } else {
                    adapter.addBonuses(nonNullContent)
                }

                progressBar.visibility = View.GONE
                btnLoadMore.isEnabled = true
                currentPage++

                // Показать/скрыть пустое состояние
                val emptyState = findViewById<TextView>(R.id.empty_state)
                if (nonNullContent.isEmpty()) {
                    emptyState.visibility = View.VISIBLE
                } else {
                    emptyState.visibility = View.GONE
                }

                // Показать "Загрузить больше", если есть следующая страница
                if (response.number < response.totalPages - 1) {
                    btnLoadMore.visibility = View.VISIBLE
                } else {
                    btnLoadMore.visibility = View.GONE
                }
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

        // Обработчик кнопки "Загрузить больше"
        btnLoadMore.setOnClickListener {
            btnLoadMore.isEnabled = false
            progressBar.visibility = View.VISIBLE
            val startDate = editStartDate.text.toString()
            val endDate = editEndDate.text.toString()
            viewModel.loadBonuses(userId = 123, page = currentPage, size = 10, startDate, endDate)
        }

        // Первая загрузка данных
        viewModel.loadBonuses(userId = 123, page = 0, size = 10, "", "")
    }
}