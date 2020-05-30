package android.com.urbanclapassignment.model

import android.provider.BaseColumns

open class KBaseColumns  {
    val _ID = "_id"
}
    class TasksContract private constructor(){
        class TasksEntry private constructor(): BaseColumns {
            companion object : KBaseColumns() {
                val TABLE_NAME = "taskslist"
                val COLUMN_TASK = "task"
                val COLUMN_STATUS = "status"
                val COLUMN_TIMESTAMP = "timestamp"
            }
        }
    }