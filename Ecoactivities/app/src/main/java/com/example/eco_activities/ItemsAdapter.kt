package com.example.eco_activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(
    private val items: List<Item>,
    private val onItemClick: (Int, String) -> Unit, // itemId, action ("open", "delete", "complete")
    private val isOrganizer: (Int) -> Boolean       // флаг: true = показываем delete/complete
) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image         : ImageView = view.findViewById(R.id.item_list_image)
        val title         : TextView  = view.findViewById(R.id.item_list_title)
        val desc          : TextView  = view.findViewById(R.id.item_list_description)
        val price         : TextView  = view.findViewById(R.id.item_list_price)
        val openButton    : Button    = view.findViewById(R.id.item_list_button)
        val buttonDelete  : Button    = view.findViewById(R.id.button_delete)
        val buttonComplete: Button    = view.findViewById(R.id.button_complete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]

        // 1) контент
        holder.image.setImageResource(getImageRes(holder.itemView.context, item.image))
        holder.title.text = item.title
        holder.desc .text = item.desc
        holder.price.text = "${item.price} ₽"

        // 2) организатор?
        if (isOrganizer(item.id)) {
            // показываем только delete/complete
            holder.openButton.visibility     = View.GONE
            holder.buttonDelete.visibility   = View.VISIBLE
            holder.buttonComplete.visibility = View.VISIBLE

            holder.buttonDelete.setOnClickListener {
                onItemClick(item.id, "delete")
            }
            holder.buttonComplete.setOnClickListener {
                onItemClick(item.id, "complete")
            }
        } else {
            // показываем только «Открыть»
            holder.buttonDelete.visibility   = View.GONE
            holder.buttonComplete.visibility = View.GONE
            holder.openButton.visibility     = View.VISIBLE

            holder.openButton.setOnClickListener {
                onItemClick(item.id, "open")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    private fun getImageRes(ctx: Context, name: String): Int =
        ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
}