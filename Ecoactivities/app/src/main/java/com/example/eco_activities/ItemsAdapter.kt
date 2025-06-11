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
    private val onItemClick: (Int, String) -> Unit, // itemId, action ("delete" или "complete")
    private val isOrganizer: (Int) -> Boolean // Флаг для определения роли пользователя
) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_description)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val buttonDelete: Button = view.findViewById(R.id.button_delete)
        val buttonComplete: Button = view.findViewById(R.id.button_complete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]

        // Загрузка изображения
        holder.image.setImageResource(getImageResourceId(holder.itemView.context, item.image))

        // Отображение данных
        holder.title.text = item.title
        holder.desc.text = item.desc
        holder.price.text = "${item.price} ₽"

        // Настройка видимости кнопок
        if (isOrganizer(item.id)) { // Correctly call the isOrganizer function
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonComplete.visibility = View.VISIBLE

            // Настройка кнопки "Удалить"
            holder.buttonDelete.setOnClickListener {
                onItemClick(item.id, "delete")
            }

            // Настройка кнопки "Провести"
            holder.buttonComplete.setOnClickListener {
                onItemClick(item.id, "complete")
            }
        } else {
            holder.buttonDelete.visibility = View.GONE
            holder.buttonComplete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    // Метод для получения ID ресурса изображения по имени файла
    private fun getImageResourceId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}