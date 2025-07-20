package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.dto.bonus.UserBonusHistoryItem
class BonusesAdapter : RecyclerView.Adapter<BonusesAdapter.BonusViewHolder>() {

    private val bonusList: MutableList<UserBonusHistoryItem> = ArrayList()

    fun setBonuses(bonuses: List<UserBonusHistoryItem>) {
        bonusList.clear()
        bonusList.addAll(bonuses)
        notifyDataSetChanged()
    }

    fun addBonuses(newBonuses: List<UserBonusHistoryItem>) {
        bonusList.addAll(newBonuses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bonus, parent, false)
        return BonusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        holder.bind(bonusList[position])
    }

    override fun getItemCount(): Int = bonusList.size

    class BonusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBonusTitle: TextView = itemView.findViewById(R.id.tv_bonus_title)
        private val tvBonusDate: TextView = itemView.findViewById(R.id.tv_bonus_date)
        private val tvBonusAmount: TextView = itemView.findViewById(R.id.tv_bonus_amount)

        fun bind(item: UserBonusHistoryItem) {
            tvBonusTitle.text = item.reason
            tvBonusDate.text = item.createdAt
            tvBonusAmount.text = "+${item.amount}"
        }
    }
}