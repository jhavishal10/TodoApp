package android.com.urbanclapassignment.model

import android.com.urbanclapassignment.model.TasksContract.TasksEntry
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TasksDBHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_TASKLIST_TABLE = "CREATE TABLE " +
                TasksEntry.TABLE_NAME + " (" +
                TasksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TasksEntry.COLUMN_TASK + " TEXT NOT NULL, " +
                TasksEntry.COLUMN_STATUS + " INTEGER NOT NULL, " +
                TasksEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+");"
        db.execSQL(SQL_CREATE_TASKLIST_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS " + TasksEntry.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "tasklist.db"
        const val DATABASE_VERSION = 1
    }
}