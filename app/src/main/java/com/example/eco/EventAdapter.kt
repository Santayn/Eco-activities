package com.example.eco
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eco.model.Event

class EventAdapter(private val events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
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
            status.text = event.status
            date.text = "Дата: ${event.date}"
            time.text = "Время: ${event.time}"
            location.text = "Место: ${event.location}"
            description.text = event.description
        }
    }
}