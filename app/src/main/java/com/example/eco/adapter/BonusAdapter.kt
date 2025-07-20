package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.bonus.UserBonusHistoryItem
import com.example.eco.api.dto.bonus.UserBonusHistoryResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class BonusesAdapter : RecyclerView.Adapter<BonusesAdapter.BonusViewHolder>() {

    private val bonusList: MutableList<UserBonusHistoryItem> = ArrayList()

    fun setBonuses(bonuses: List<UserBonusHistoryItem>) {
        bonusList.clear()
        bonusList.addAll(bonuses)
        notifyDataSetChanged()
    }

    fun addBonuses(bonuses: List<UserBonusHistoryItem>) {
        bonusList.addAll(bonuses)
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
        private val title: TextView = itemView.findViewById(R.id.tv_bonus_title)
        private val date: TextView = itemView.findViewById(R.id.tv_bonus_date)
        private val amount: TextView = itemView.findViewById(R.id.tv_bonus_amount)

        fun bind(item: UserBonusHistoryItem) {
            title.text = item.reason
            date.text = item.createdAt
            amount.text = "+${item.amount}"
        }
    }
}