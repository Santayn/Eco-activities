package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.dto.bonus.BonusHistoryDto
import java.text.SimpleDateFormat
import java.util.Locale

class BonusAdapter(internal var bonuses: List<BonusHistoryDto>) : RecyclerView.Adapter<BonusAdapter.BonusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bonus, parent, false)
        return BonusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val bonus = bonuses[position]
        holder.bind(bonus)
    }

    override fun getItemCount(): Int = bonuses.size

    inner class BonusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_bonus_title)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_bonus_date)
        private val tvAmount: TextView = itemView.findViewById(R.id.tv_bonus_amount)

        fun bind(bonus: BonusHistoryDto) {
            // Отображение названия бонуса
            tvTitle.text = bonus.bonusType.name

            // Отображение даты
            val dateFormat = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
            val date = dateFormat.format(bonus.createdAt)
            tvDate.text = date

            // Отображение количества бонусов
            tvAmount.text = "+${bonus.amount}"
            if (bonus.amount > 0) {
                tvAmount.setTextColor(android.graphics.Color.GREEN)
            } else {
                tvAmount.setTextColor(android.graphics.Color.RED)
            }
        }
    }
}