package com.example.eco.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_operations)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BonusAdapter(emptyList())
        recyclerView.adapter = adapter

        val totalBalanceTextView = view.findViewById<TextView>(R.id.tv_total_balance)

        viewModel = ViewModelProvider(this)[BonusViewModel::class.java]

        viewModel.bonusHistory.observe(viewLifecycleOwner) { bonuses ->
            adapter.bonuses = bonuses
            adapter.notifyDataSetChanged()
            totalBalanceTextView.text = viewModel.totalBalance.value.toString()
        }

        viewModel.fetchBonusHistory(1) // TODO: Получи реальный ID пользователя
    }
}