package com.harsh.recyclerapplication

import android.content.Context
import android.nfc.Tag
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TaskRecyclerAdapter(
    var context: Context,
    var list: ArrayList<TaskDataClass>,
    var taskClickInterface: TaskClickInterface
) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvTittle = view.findViewById<TextView>(R.id.etTittle)
        var tvDescription = view.findViewById<TextView>(R.id.etDescription)
        var btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Log.e(Tag,"onBindViewHolder:$position ",)
        holder.tvTittle.setText(list[position].tvTittle)
        holder.tvDescription.setText(list[position].tvDescription)
        when (list[position].tvPriority) {
            1 -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            }

            2 -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green))

            }

            3 -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            }
        }

        holder.btnUpdate.setOnClickListener {
            taskClickInterface.updateTask(position)
            //Toast.makeText(context, "Update clicked", Toast.LENGTH_SHORT).show()
        }
        holder.btnDelete.setOnClickListener {
            taskClickInterface.deleteTask(position)
        }
    }
}