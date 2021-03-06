package me.mikemodder.simpletodo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView

import me.mikemodder.simpletodo.database.DatabaseUtil


class MainActivity : AppCompatActivity() {

    val TAG = "TodosApp"
    lateinit var todos: ArrayList<Todo>
    lateinit var todosAdapter: TodoAdapter
    lateinit var lvTodos: ListView
    lateinit var DBUtil: DatabaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "App created!")
        setContentView(R.layout.activity_main)

        DBUtil = DatabaseUtil(super.getApplicationContext())
        DBUtil.check()

        lvTodos = findViewById(R.id.lvTodos)
        todos = DBUtil.getTodos()
        todosAdapter = TodoAdapter(super.getApplicationContext(), todos)

        lvTodos.adapter = todosAdapter

        lvTodos.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val deleteTodo = todosAdapter.getItem(i)
            todosAdapter.remove(deleteTodo)
            DBUtil.removeTodo(deleteTodo)
            true
        }

    }

    fun addListItem(view: View?) {
        val inputBox = findViewById<EditText>(R.id.inputBox)
        if (inputBox.text.toString() == "") return
        if(inputBox.text.toString() == "db::nuke") {
            DBUtil.nukeDB()
            return redoList()
        }

        val newTodo = Todo(inputBox.text.toString())

        DBUtil.addTodo(newTodo)
        redoList()
        inputBox.text.clear()
    }

    fun redoList() {
        // Clear the ListView and re-add stuff from the DB
        // Not the best way of doing this, as every change will cause the list to be redone
        // However, this is my lazy fix to removing todos easily
        todosAdapter.clear()
        todosAdapter.addAll(DBUtil.getTodos())
    }

}
