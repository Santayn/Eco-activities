package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.dto.bonus.UserBonusHistoryItem

class BonusesAdapter : RecyclerView.Adapter<BonusesAdapter.BonusViewHolder>() {

    private var bonusList: MutableList<UserBonusHistoryItem> = ArrayList()

    fun setBonuses(bonuses: List<UserBonusHistoryItem>) {
        this.bonusList.clear()
        this.bonusList.addAll(bonuses)
        notifyDataSetChanged()
    }

    fun addBonuses(newBonuses: List<UserBonusHistoryItem>) {
        this.bonusList.addAll(newBonuses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bonus, parent, false)
        return BonusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val item = bonusList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return bonusList.size
    }

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