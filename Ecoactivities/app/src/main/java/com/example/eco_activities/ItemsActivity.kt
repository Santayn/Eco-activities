package com.example.eco_activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        val itemsList: RecyclerView = findViewById(R.id.itemslist)
        val items = arrayListOf<Item>()
        items.add(Item(1,"first","первый","123141241241324124214","111111111111111111111111111111111111111111111111111",123))
        items.add(Item(1,"second","второй","sfaasffassafsfasfasfa","222222222222222222222222222222222222222222222222222",234))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter =ItemsAdapter(items, this)
    }
}