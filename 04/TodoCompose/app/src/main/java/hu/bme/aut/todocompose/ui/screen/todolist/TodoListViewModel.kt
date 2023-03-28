package hu.bme.aut.todocompose.ui.screen.todolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import hu.bme.aut.todocompose.data.TodoItem
import hu.bme.aut.todocompose.data.TodoPriority
import kotlin.random.Random

class TodoListViewModel : ViewModel() {
    private var _todoList = mutableStateListOf<TodoItem>()

    fun getAllToDoList(): List<TodoItem> {
        return _todoList;
    }

    fun getAllTodoNum(): Int {
        return _todoList.size
    }

    fun getImportantTodoNum(): Int {
        return _todoList.count { it.priority==TodoPriority.HIGH }
    }

    fun addTodoList(todoItem: TodoItem) {
        _todoList.add(todoItem)
    }


    fun removeTodoItem(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun clearAllTodos() {
        _todoList.clear()
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val index = _todoList.indexOf(todoItem)

        val newTodo = todoItem.copy(
            title = todoItem.title,
            description = todoItem.description,
            createDate = todoItem.createDate,
            priority = todoItem.priority,
            isDone = value
        )

        _todoList[index] = newTodo
    }
}