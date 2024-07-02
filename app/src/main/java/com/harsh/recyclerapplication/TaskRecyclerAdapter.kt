package com.harsh.recyclerapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskRecyclerAdapter(var list : ArrayList<TaskDataClass>) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var tvTittle = view.findViewById<TextView>(R.id.tvTittle)
        var tvDescription = view.findViewById<TextView>(R.id.tvDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTittle.setText(list[position].tvTittle)
        holder.tvDescription.setText(list[position].tvDescription)
    }
}