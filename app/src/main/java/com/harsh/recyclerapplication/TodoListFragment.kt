package com.harsh.recyclerapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson

import com.harsh.recyclerapplication.databinding.CustomdialogBinding
import com.harsh.recyclerapplication.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(), TaskClickInterface {
    var binding: FragmentTodoListBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    lateinit var adapter: TaskRecyclerAdapter
    lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskRecyclerAdapter(requireContext(), list, this)
        todoDatabase = TodoDatabase.getInstance(requireContext())

        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.recyclerView?.layoutManager = linearLayoutManager
        binding?.recyclerView?.adapter = adapter
        binding?.rbMediumPriority?.setOnClickListener {
            list.clear()
            list.addAll(
                todoDatabase.todoDao().TaskAccPriorit(
                    2
                )
            )

            adapter.notifyDataSetChanged()
        }
        binding?.rbAll?.setOnClickListener {
            list.clear()
            getList()
            adapter.notifyDataSetChanged()
        }
        binding?.rbLowPriority?.setOnClickListener {
            list.clear()
            list.addAll(
                todoDatabase.todoDao().TaskAccPriorit(
                    3
                )
            )
            adapter.notifyDataSetChanged()
        }
        binding?.rbHighPriority?.setOnClickListener {
            list.clear()
            list.addAll(
                todoDatabase.todoDao().TaskAccPriorit(
                    1
                )
            )
            adapter.notifyDataSetChanged()
        }
        binding?.fabBtn?.setOnClickListener {
            var dialog = Dialog(requireContext())
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
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_description)
                } else if (dialogBinding.radioButton.checkedRadioButtonId == -1) {
                    Toast.makeText(requireContext(), "choose one option", Toast.LENGTH_SHORT).show()
                } else {
                    var priority = if (dialogBinding.rbLow.isChecked)
                        1
                    else if (dialogBinding.rbMedium.isChecked)
                        2
                    else if (dialogBinding.rbHigh.isChecked)
                        3
                    else
                        0
                    /*  list.add(
                          TaskDataClass(
                              tvTittle = dialogBinding.etTittle.text.toString(),
                              tvDescription = dialogBinding.etDescription.text.toString(),
                              tvPriority = priority
                          )
                      )*/
                    todoDatabase.todoDao().insertToDo(
                        TaskDataClass(
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
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
        Toast.makeText(requireContext(), "Update clicked in recycler", Toast.LENGTH_SHORT).show()
        Dialog(requireContext()).apply {
            var dialogBinding = CustomdialogBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            getWindow()?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            show()
            dialogBinding.etTittle.setText(list[position].tvTittle)
            dialogBinding.etDescription.setText(list[position].tvDescription)
            when (list[position].tvPriority) {
                0 -> dialogBinding.rbLow.isChecked = true
                1 -> dialogBinding.rbMedium.isChecked = true
                2 -> dialogBinding.rbHigh.isChecked = true
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTittle.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_tittle)
                } else if (dialogBinding.etDescription.text.toString().isEmpty()) {
                    dialogBinding.etTittle.error = resources.getString(R.string.enter_description)
                } else if (dialogBinding.radioButton.checkedRadioButtonId == -1) {
                    Toast.makeText(requireContext(), "select one", Toast.LENGTH_SHORT).show()
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
                    todoDatabase.todoDao().updateTodo(
                        TaskDataClass(
                            id = list[position].id,
                            tvTittle = dialogBinding.etTittle.text.toString(),
                            tvDescription = dialogBinding.etDescription.text.toString(),
                            tvPriority = priority
                        )
                    )
                    getList()
                    /*  list.set(
                          position, TaskDataClass(
                              tvTittle = dialogBinding.etTittle.text.toString(),
                              tvDescription = dialogBinding.etDescription.text.toString(),
                              tvPriority = priority
                          )
                      )
                      adapter.notifyDataSetChanged()*/
                    dismiss()
                }
            }
        }
    }

    fun getList() {
        list.clear()
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
       todoDatabase.todoDao().deleteToDo(list[position])
        getList()
    }

    override fun itemClick(position: Int) {
        var convertToString = Gson().toJson(list[position])
        findNavController().navigate(R.id.singleNotesFragment)
        bundleOf("notes" to convertToString)
    }

}