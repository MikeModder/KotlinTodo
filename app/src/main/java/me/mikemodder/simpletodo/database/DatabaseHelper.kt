package me.mikemodder.simpletodo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx, "TodoDB", null, 1) {

    val TAG = "DBHelper"
    val CREATE_DB = "CREATE TABLE IF NOT EXISTS `todos` (\n" +
            " `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            " `text` TEXT NOT NULL,\n" +
            " `done` INTEGER NOT NULL DEFAULT 0\n" +
            ");"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "Creating database... Schema:\n $CREATE_DB")
        db?.execSQL(CREATE_DB)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "Database upgrade needed. ($oldVersion -> $newVersion). Data will be wiped")
        db?.execSQL("DROP TABLE IF EXISTS `todos`")
        onCreate(db)
    }

    fun nuke(db: SQLiteDatabase?) {
        Log.d(TAG, "Nuking and recreating DB...")
        db?.execSQL("DROP TABLE IF EXISTS `todos`")
        onCreate(db)
    }

    fun safetyCheck(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_DB)
    }
}