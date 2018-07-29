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
        val fields = arrayOf("_id", "text", "done")
        var cursor = db.query("todos", fields, null, null, null, null, null)
        if(cursor == null) return ArrayList()
        val todos = ArrayList<Todo>()
        Log.d(TAG, "Queried DB, loading ${cursor.count} todos...")
        if(cursor.count == 0) return ArrayList()
        //cursor.moveToFirst()

        while(cursor.moveToNext()){
            val id = cursor.getInt(0)
            Log.d(TAG, "Current entity has ${cursor.columnCount} columns. Names: ${cursor.columnNames.joinToString()}")
            val text = cursor.getString(1)
            var done: Boolean = false
            when(cursor.getInt(2)){
                0 -> done = false
                1 -> done = true
                else -> Log.d(TAG, "Int at pos 1 wasn't 1/0 (${cursor.getInt(1)})")
            }
            Log.d(TAG, "Read from DB: $text (done? $done, id: $id)")
            todos.add(Todo(text, done, id))
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

    fun removeTodo(todo: Todo) {
        // Removes one, although it's done via the content so if there's any duplicates, they'll be removed too
        Log.d(TAG, "Deleting todo with _id ${todo.id}")
        db.delete("todos", "`_id` = ?", arrayOf(todo.id.toString()))
    }

    /*fun toggleDone(todoId: Int) {

    }*/

    fun nukeDB() {
        helper.nuke(db)
    }

    fun check() {
        helper.safetyCheck(db)
    }
}