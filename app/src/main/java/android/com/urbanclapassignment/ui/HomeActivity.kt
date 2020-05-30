package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.com.urbanclapassignment.model.TasksState
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_new_task_layout.view.*


class HomeActivity : AppCompatActivity(), AdapterCallbackInterface {
    private val adapter = TasksAdapter(this)
    private lateinit var viewModel: ViewModel


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
                is TasksState.Data -> adapter.addData(it.list)
                is TasksState.Clear -> adapter.clearItems()
            }
        })
    }

    private fun initUi() {
        taskRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        taskRecyclerView.adapter = adapter
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
                        Toast.LENGTH_LONG
                    ).show()

                    viewModel.addItem(mView.taskText.text.toString())
                }
                alertDialog.dismiss()
            }
        }
    }

    override fun undoClicked(task: ListItem) {
        viewModel.undoClicked(task)
    }

    override fun deleteClicked(task: ListItem) {
        viewModel.deleteClicked(task)
    }

    override fun markDoneClicked(task: ListItem, pos: Int) {
        viewModel.markDone(task)
        initUi()
    }

    override fun taskClicked(task: ListItem) {
        //open task editor Ui for this
    }

}