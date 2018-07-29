package me.mikemodder.simpletodo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class TodoAdapter(ctx: Context, todos: ArrayList<Todo>) : ArrayAdapter<Todo>(ctx, 0, todos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val todo = getItem(position)
        var convertViewB: View
        if(convertView == null) {
            convertViewB = LayoutInflater.from(context).inflate(R.layout.todo_row, parent, false)
            val tvText = convertViewB.findViewById<TextView>(R.id.todoText)
            val isDone = convertViewB.findViewById<CheckBox>(R.id.isDone)
            tvText.text = todo.text
            isDone.isChecked = !todo.done

            return convertViewB
        }

        val tvText = convertView.findViewById<TextView>(R.id.todoText)
        val isDone = convertView.findViewById<CheckBox>(R.id.isDone)

        tvText.text = todo.text
        isDone.isChecked = !todo.done

        return convertView

    }
}