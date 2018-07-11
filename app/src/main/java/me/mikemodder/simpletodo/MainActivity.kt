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
            todosAdapter.remove(todosAdapter.getItem(i))
            true
        }

    }

    fun addListItem(view: View?) {
        val inputBox = findViewById<EditText>(R.id.inputBox)
        if (inputBox.text.toString() == "") return
        if(inputBox.text.toString() == "db::nuke") return DBUtil.nukeDB()

        val newTodo = Todo(inputBox.text.toString())

        todosAdapter.add(newTodo)
        DBUtil.addTodo(newTodo)
        inputBox.text.clear()
    }

}
