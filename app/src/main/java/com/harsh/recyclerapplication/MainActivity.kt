package com.harsh.recyclerapplication

import android.app.Dialog
import android.app.ProgressDialog.show
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsh.recyclerapplication.databinding.ActivityMainBinding
import com.harsh.recyclerapplication.databinding.CustomdialogBinding
import com.harsh.recyclerapplication.databinding.ItemTaskBinding

class MainActivity : AppCompatActivity(), TaskClickInterface {
    var binding: ActivityMainBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter: TaskRecyclerAdapter = TaskRecyclerAdapter(this, list, this)
    lateinit var todoDatabase: TodoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        todoDatabase = TodoDatabase.getInstance(this)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerView?.layoutManager = linearLayoutManager
        binding?.recyclerView?.adapter = adapter
        binding?.fabBtn?.setOnClickListener {
            var dialog = Dialog(this)
            var dialogBinding = CustomdialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            getWindow()?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.show()

            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTittle.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_tittle)
                } else if (dialogBinding.etDescription.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_description)
                } else if (dialogBinding.radioButton.checkedRadioButtonId == -1) {
                    Toast.makeText(this, "choose one option", Toast.LENGTH_SHORT).show()
                } else {
                    var priority = if (dialogBinding.rbLow.isChecked)
                        1
                    else if (dialogBinding.rbMedium.isChecked)
                        2
                    else if (dialogBinding.rbHigh.isChecked)
                        3
                    else
                        0
                    list.add(
                        TaskDataClass(
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
                        )
                    )
                    todoDatabase.todoDao().insertToDo(
                        TaskDataClass(
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
                        )
                    )
                    todoDatabase.todoDao().updateTodo(
                        TaskDataClass(
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
                        )
                    )
                    todoDatabase.todoDao().deleteToDo(
                        TaskDataClass(
                            tvPriority = priority,
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString()
                        )
                    )
                    getList()
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }

            }
        }
        getList()
    }

    override fun updateTask(position: Int) {
        Dialog(this).apply {
            var dialogBinding = CustomdialogBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            getWindow()?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTittle.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_tittle)
                } else if (dialogBinding.etDescription.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_description)
                } else if (dialogBinding.radioButton.checkedRadioButtonId == -1) {
                    Toast.makeText(this@MainActivity, "select one", Toast.LENGTH_SHORT).show()
                } else {
                    var priority = if (dialogBinding.rbLow.isChecked)
                        1
                    else if (dialogBinding.rbMedium.isChecked)
                        2
                    else if (dialogBinding.rbHigh.isChecked)
                        3
                    else {
                        0
                    }
                    list.set(
                        position, TaskDataClass(
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
                        )
                    )
                    adapter.notifyDataSetChanged()
                    dismiss()
                }
            }
        }
    }

    fun getList() {
        list.addAll(todoDatabase.todoDao().getList())
        adapter.notifyDataSetChanged()
    }
  /*  fun updateToDO(){
      list.add(
          TaskDataClass( tvTittle = toString(), tvDescription = toString())
      )
        adapter.notifyDataSetChanged()
    }*/
  /*  fun deleteToDO(){
        list.removeAt(
            TaskDataClass( tvTittle = toString(), tvDescription = toString())
        )
        adapter.notifyDataSetChanged()
    }*/

    override fun deleteTask(position: Int) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("are you sure to delete?:")
        alertDialog.setPositiveButton("yes") { _, _ ->
            list.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton("No") { _, _ ->
        }
        alertDialog.show()
    }

}