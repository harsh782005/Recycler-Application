package com.harsh.recyclerapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ToDoItemRecycler(var toDoEntity: ArrayList<ToDoEntity>) :
    RecyclerView.Adapter<ToDoItemRecycler.ViewHolder>() {
    class ViewHolder(var view : View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view =
           LayoutInflater.from(parent.context).inflate(R.layout.item_to_do_recycler,parent,
               false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return toDoEntity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}