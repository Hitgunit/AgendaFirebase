package com.example.agendafirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ItemAdapter (val itemList: ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    //
    var onItemClick: ((Item) -> Unit) ?= null
    class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.txtName)
        val phone: TextView = itemView.findViewById(R.id.txtTelefono)
        val email: TextView = itemView.findViewById(R.id.txtCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var item = itemList[position]
        holder.name.text = item.nombre
        holder.phone.text = item.telefono
        holder.email.text = item.correo

        //
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }
}