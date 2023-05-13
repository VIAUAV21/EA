package hu.ait.tododemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.tododemo.MainApplication
import hu.ait.tododemo.data.TodoDAO
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoDAO: TodoDAO,
) : ViewModel() {

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodos()
    }

    suspend fun getAllTodoNum(): Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum(): Int {
        return todoDAO.getImportantTodosNum()
    }


    fun addTodoList(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.insert(todoItem)
        }
    }

    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.delete(todoItem)
        }
    }

    fun clearAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.deleteAllTodos()
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val newTodoItem = todoItem.copy()
        newTodoItem.isDone = value
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.update(newTodoItem)
        }
    }

    fun editTodoItem(todoItemEdited: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.update(todoItemEdited)
        }
    }
}


