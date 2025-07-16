package com.example.eco.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.R
import com.example.eco.api.dto.event.Event
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val events: MutableList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    fun submitList(newList: List<Event>) {
        events.clear()
        events.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val status = itemView.findViewById<TextView>(R.id.status)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val time = itemView.findViewById<TextView>(R.id.time)
        private val location = itemView.findViewById<TextView>(R.id.location)
        private val description = itemView.findViewById<TextView>(R.id.description)

        fun bind(event: Event) {
            title.text = event.title
            description.text = event.description
            location.text = "Место: ${event.location}"

            val parsed = parseDateTime(event.startTime)
            date.text = "Дата: ${parsed.first}"
            time.text = "Время: ${parsed.second}"
            status.text = if (event.conducted) "ПРОВЕДЕНО" else "ПРЕДСТОИТ"
        }

        private fun parseDateTime(dateTime: String): Pair<String, String> {
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = sdf.parse(dateTime)
                val day = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date!!)
                val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
                Pair(day, time)
            } catch (e: Exception) {
                Pair("неизвестно", "неизвестно")
            }
        }
    }
}