package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.com.urbanclapassignment.model.TasksState
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_new_task_layout.view.*


class HomeActivity : AppCompatActivity(), AdapterCallbackInterface {
    private val adapter = TasksAdapter(this)
    private lateinit var viewModel: ViewModel
    private var tasksList = mutableListOf<ListItem>()
    private var searchBarOn = false
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
        initUi()
    }

    private fun initViewModel() {
        viewModel = AndroidViewModelFactory(application).create(ViewModel::class.java)
        viewModel.tasksLiveData.observe(this, Observer {
            when (it) {
                is TasksState.Data -> {
                    tasksList = it.list.toMutableList()
                    if (!searchBarOn)
                        adapter.addData(it.list)
                    else filter(searchText)
                }
                is TasksState.Clear -> adapter.clearItems()
            }
        })
    }

    private fun initUi() {
        taskRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        taskRecyclerView.adapter = adapter
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(taskRecyclerView)
        searchButton.debouncedOnClick {
            searchBarOn = true
            searchEditText.isVisible = true
            searchClose.isVisible = true
        }
        searchClose.debouncedOnClick {
            searchBarOn = false
            searchEditText.isVisible = false
            searchClose.isVisible = false
            searchEditText.text.clear()
            adapter.clearItems()
            adapter.addData(tasksList)
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                searchText = s.toString()
                filter(s.toString())
            }
        })

        addNewTaskButton.debouncedOnClick {
            val builder = AlertDialog.Builder(this)
            val mView: View = layoutInflater.inflate(R.layout.add_new_task_layout, null)
            builder.setView(mView)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
            mView.addTaskButton.debouncedOnClick {
                if (!mView.taskText.text.isNullOrEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        mView.taskText.text.toString()
                            .substring(0, mView.taskText.text.toString().length) + "........Added",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.addItem(mView.taskText.text.toString())
                }
                alertDialog.dismiss()
            }
        }
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<ListItem>()
        for (item in tasksList) {
            if (item.taskName.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter.clearItems()
        adapter.filterList(filteredList)
    }

    override fun undoClicked(task: ListItem) {
        viewModel.undoClicked(task)
    }

    override fun deleteClicked(task: ListItem) {
        viewModel.deleteClicked(task)
    }

    override fun markDoneClicked(task: ListItem, pos: Int) {
        Handler().postDelayed({
            viewModel.markDone(task, pos)
        }, 500)

    }

    override fun taskClicked(task: ListItem) {
        //open task editor Ui for this future extension for better UI
    }

}