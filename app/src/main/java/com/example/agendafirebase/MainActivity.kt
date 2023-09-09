package com.example.agendafirebase

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
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
        itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))

        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter

    }
}