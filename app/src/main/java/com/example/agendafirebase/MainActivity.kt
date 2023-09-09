package com.example.agendafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var itemList: ArrayList<Item>
    lateinit var recyclerView: RecyclerView
    lateinit var itemAdapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemList = ArrayList()

        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Jose", "4521204043", "jose123@hotmail.com"))
        itemList.add(Item("Maria", "4521597865", "maria@hotmail.com"))

        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter

        //
        itemAdapter.onItemClick = {
            val intent = Intent(this, ItemDetailed::class.java)
            intent.putExtra("item", it)
            startActivity(intent)
        }

    }
}