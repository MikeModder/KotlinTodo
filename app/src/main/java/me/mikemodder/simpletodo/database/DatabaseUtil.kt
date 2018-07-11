package me.mikemodder.simpletodo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import me.mikemodder.simpletodo.Todo

class DatabaseUtil(ctx: Context) {

    val TAG = "DBUtil"

    private val helper = DatabaseHelper(ctx)
    private val db = helper.writableDatabase

    fun getTodos(): ArrayList<Todo> {
        val fields = arrayOf("text", "done")
        var cursor = db.query("todos", fields, null, null, null, null, null)
        if(cursor == null) return ArrayList()
        val todos = ArrayList<Todo>()
        Log.d(TAG, "Queried DB, loading ${cursor.count} todos...")
        if(cursor.count == 0) return ArrayList()
        //cursor.moveToFirst()

        while(cursor.moveToNext()){
            val text = cursor.getString(0)
            var done: Boolean = false
            when(cursor.getInt(1)){
                0 -> done = false
                1 -> done = true
            }
            Log.d(TAG, "Read from DB: $text (done? $done)")
            todos.add(Todo(text, done))
        }

        return todos
    }

    fun addTodo(todo: Todo): Long {
        var values = ContentValues()
        var done = 0
        if(todo.done) done = 1
        values.put("text", todo.text)
        values.put("done", done)

        return db.insert("todos", null, values)

    }

    fun nukeDB() {
        helper.nuke(db)
    }

    fun check() {
        helper.safetyCheck(db)
    }
}