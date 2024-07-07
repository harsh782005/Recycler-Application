package com.harsh.recyclerapplication

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.harsh.recyclerapplication.databinding.CustomtTodoBinding
import com.harsh.recyclerapplication.databinding.FragmentSingleNotesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleNotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentSingleNotesBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var todoEntity = arrayListOf<ToDoEntity>()
    var toDoItemRecycler = ToDoItemRecycler(todoEntity)
    var todoDatabase: TodoDatabase? = null
    var taskDataClass = TaskDataClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleNotesBinding.inflate(layoutInflater)
        return binding?.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_single_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var notes = it.getString("notes")
            taskDataClass = Gson().fromJson(notes, TaskDataClass::class.java)
            binding?.etTodoTittle?.setText(taskDataClass.tvTittle)
            binding?.etTodoDescription?.setText(taskDataClass.tvDescription)
            getList()
        }
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.Recyclerview?.adapter = toDoItemRecycler
        binding?.Recyclerview?.layoutManager = linearLayoutManager
        binding?.fabToDo?.setOnClickListener {
            if (binding?.etTodoTittle?.text?.toString().isNullOrEmpty()) {
                binding?.etTodoTittle?.error = resources.getString(R.string.enter_tittle)
            } else if (binding?.etTodoDescription?.text?.toString().isNullOrEmpty()) {
                binding?.etTodoDescription?.error = resources.getString(R.string.enter_description)
            } else {
                Dialog(requireContext()).apply {
                    var dialogBinding = CustomtTodoBinding.inflate(layoutInflater)
                    setContentView(dialogBinding.root)
                    show()
                    dialogBinding.btnDone.setOnClickListener {
                        if (dialogBinding.etCustomTodo.text?.toString().isNullOrEmpty() == false) {
                            dialogBinding.etCustomTodo.error =
                                resources.getString(R.string.enter_tittle)
                        } else {
                            todoDatabase?.todoDao()?.insertTodoItem(
                                ToDoEntity(
                                    todo = dialogBinding.etCustomTodo.text.toString(),
                                )
                            )
                            getToDoList()
                            
                        }

                    }
                }
            }
        }
        getToDoList()

    }

    private fun getToDoList() {
        todoDatabase?.todoDao()?.getToDoList()
    }

    fun getList() {
        todoDatabase?.todoDao()?.getList()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleNotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}