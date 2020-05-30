package android.com.urbanclapassignment.ui

import android.app.Application
import android.com.urbanclapassignment.model.ListItem
import android.com.urbanclapassignment.model.TasksContract
import android.com.urbanclapassignment.model.TasksDBHelper
import android.com.urbanclapassignment.model.TasksState
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var database: SQLiteDatabase? = null
    private val _tasksLiveData = MutableLiveData<TasksState>()
    val tasksLiveData: LiveData<TasksState> = _tasksLiveData
    private val tasksList = mutableListOf<ListItem>()
    private val context = getApplication<Application>().applicationContext


    init {
        loadData()
    }

    private fun loadData() {
        tasksList.clear()
        var i = 0
        var cursor = getAllItems()
        while (cursor != null && cursor.moveToNext() && i < cursor.count) {
            val taskId: String =
                cursor.getString(0)
            val name: String =
                cursor.getString(1)
            val status: String =
                cursor.getString(2)
            tasksList.add(ListItem(taskId.toLong(), name, status.toInt()))
            i++
        }
        _tasksLiveData.value = TasksState.Data(tasksList)
    }

    private fun getAllItems(): Cursor? {
        val dbHelper = TasksDBHelper(context)
        database = dbHelper.writableDatabase
        return database?.query(
            TasksContract.TasksEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null, TasksContract.TasksEntry.COLUMN_TIMESTAMP + " DESC"
        )
    }

    fun markDone(
        task: ListItem
    ) {
        val cv = ContentValues()
        cv.put("task", task.taskName)
        cv.put("status", 1)
        try {
            database?.update(
                TasksContract.TasksEntry.TABLE_NAME,
                cv,
                TasksContract.TasksEntry._ID + "=" + task.taskId,
                null
            )
            database?.close()
            _tasksLiveData.value = TasksState.Clear
            loadData()
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
        Log.e("Invalid", "Invalid Data, Check your data first")
    }

    fun addItem(task: String) {
        val name: String = task
        val cv = ContentValues()
        cv.put(TasksContract.TasksEntry.COLUMN_TASK, name)
        cv.put(TasksContract.TasksEntry.COLUMN_STATUS, 0)
        database?.insert(TasksContract.TasksEntry.TABLE_NAME, null, cv)
        _tasksLiveData.value = TasksState.Clear
        loadData()
    }

    fun undoClicked(task: ListItem) {
        val cv = ContentValues()
        cv.put("task", task.taskName)
        cv.put("status", 0)
        try {
            database?.update(
                TasksContract.TasksEntry.TABLE_NAME,
                cv,
                TasksContract.TasksEntry._ID + "=" + task.taskId,
                null
            )
            database?.close()
            _tasksLiveData.value = TasksState.Clear
            loadData()
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
        Log.e("Invalid", "Invalid Data, Check your data first")
    }

    fun deleteClicked(task: ListItem) {
        val dbHelper = TasksDBHelper(context)
        database = dbHelper.writableDatabase
        database?.delete(
            TasksContract.TasksEntry.TABLE_NAME,
            TasksContract.TasksEntry._ID + "=" + task.taskId, null
        )
        _tasksLiveData.value = TasksState.Clear
        loadData()
    }
}