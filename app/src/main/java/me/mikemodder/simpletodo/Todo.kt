package me.mikemodder.simpletodo

class Todo(todoText: String, done: Boolean = false, id: Int = -1) {
    var text: String = todoText
    var done: Boolean = done
    var id: Int = id
}