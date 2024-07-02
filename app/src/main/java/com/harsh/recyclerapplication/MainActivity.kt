package com.harsh.recyclerapplication

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsh.recyclerapplication.databinding.ActivityMainBinding
import com.harsh.recyclerapplication.databinding.CustomdialogBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter: TaskRecyclerAdapter = TaskRecyclerAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
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
            dialog.getWindow()?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT

            )
            dialog.show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTittle.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_tittle)
                } else if (dialogBinding.etDescription.text.toString().isEmpty()) {
                    dialogBinding.etDescription.error =
                        resources.getString(R.string.enter_description)
                } else if (dialogBinding.radioButton.checkedRadioButtonId == -1) {
                    Toast.makeText(this, "Select One", Toast.LENGTH_SHORT).show()
                } else {
                    list.add(
                        TaskDataClass(
                            dialogBinding.etTittle.text.toString(),
                            dialogBinding.etDescription.text.toString()
                        )
                    )
                    dialogBinding.rbLow.setOnClickListener {
                        binding?.recyclerView?.setBackgroundColor(Color.parseColor("#FF5722"))
                    }
                    dialogBinding.rbHigh.setOnClickListener {
                        binding?.recyclerView?.setBackgroundColor(Color.parseColor("3F51B5"))
                    }
                    dialogBinding.rbMedium.setOnClickListener {
                        binding?.recyclerView?.setBackgroundColor(Color.parseColor("3F51B5"))
                    }
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
    }
}