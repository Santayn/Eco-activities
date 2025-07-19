package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.dto.bonus.BonusHistoryDto
import com.example.eco.api.dto.bonus.BonusTypeDTO
import java.text.SimpleDateFormat
import java.util.Locale

class BonusAdapter(private var bonuses: List<BonusHistoryDto>) :
    RecyclerView.Adapter<BonusAdapter.BonusViewHolder>() {

    fun updateData(newList: List<BonusHistoryDto>) {
        bonuses = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bonus, parent, false)
        return BonusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        holder.bind(bonuses[position])
    }

    override fun getItemCount(): Int = bonuses.size

    inner class BonusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_bonus_title)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_bonus_date)
        private val tvAmount: TextView = itemView.findViewById(R.id.tv_bonus_amount)

        fun bind(bonus: BonusHistoryDto) {
            tvTitle.text = bonus.bonusType.name

            val dateFormat = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
            tvDate.text = dateFormat.format(bonus.createdAt)

            tvAmount.text = "+${bonus.amount}"
            tvAmount.setTextColor(
                if (bonus.amount > 0) android.graphics.Color.GREEN
                else android.graphics.Color.RED
            )
        }
    }
}