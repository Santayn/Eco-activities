package com.example.eco.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.adapter.BonusAdapter
import com.example.eco.ui.viewmodel.BonusViewModel

class BonusesFragment : Fragment() {

    private lateinit var viewModel: BonusViewModel
    private lateinit var adapter: BonusAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bonuses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_operations)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BonusAdapter(emptyList())
        recyclerView.adapter = adapter

        // TextView для баланса
        val totalBalanceTextView = view.findViewById<TextView>(R.id.tv_total_balance)

        // EditText для поиска
        val editSearch = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.edit_search)

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[BonusViewModel::class.java]

        // Подписка на обновления
        viewModel.bonusHistory.observe(viewLifecycleOwner) { result ->
            adapter.updateData(result.content)
            totalBalanceTextView.text = viewModel.totalBalance.value.toString()
        }

        // Получение ID пользователя
        val sharedPref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        if (userId != -1) {
            viewModel.fetchBonusHistory(userId)
        } else {
            Log.e("BonusesFragment", "Ошибка: user_id не найден")
        }

        // Обработка поиска
        editSearch.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val query = editSearch.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchBonusTypes(query, null, 0, 10)
                } else {
                    viewModel.fetchBonusHistory(userId)
                }
            }
        }
    }
}