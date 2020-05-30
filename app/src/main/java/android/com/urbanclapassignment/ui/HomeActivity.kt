package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_new_task_layout.view.*


class HomeActivity : AppCompatActivity(), AdapterCallbackInterface {
    private val adapter = TasksAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUi()
    }

    private fun initUi() {
        addNewTaskButton.debouncedOnClick {
            val builder = AlertDialog.Builder(this)
            val mView: View = layoutInflater.inflate(R.layout.add_new_task_layout, null)
            builder.setView(mView)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            mView.addTaskButton.debouncedOnClick {
                if (!mView.taskText.text.isNullOrEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        mView.taskText.text.toString()
                            .substring(0, mView.taskText.text.toString().length) + "........Added",
                        Toast.LENGTH_LONG
                    ).show()
                    //add new task in the database
                }
                alertDialog.dismiss()
            }

        }
        val task = mutableListOf<ListItem>()
        adapter.addData(task)
        taskRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        taskRecyclerView.adapter = adapter
    }

    override fun undoClicked(id: Int) {
        // change the status of task in database
    }

    override fun deleteClicked(id: Int) {
        //delete this task from database
    }

    override fun markDoneClicked(id: Int) {
        // mark this task done in database
    }

    override fun taskClicked(id: Int) {
        //open task editor Ui for this
    }
}