package hu.bme.aut.todocompose.ui.screen.todolist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.todocompose.ui.theme.TodoComposeTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import hu.bme.aut.todocompose.R
import hu.bme.aut.todocompose.data.TodoItem
import hu.bme.aut.todocompose.data.TodoPriority
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    todoListViewModel: TodoListViewModel = viewModel(),
    summaryClickHandler: (Int, Int) -> Unit
) {
    var showAddDialog by remember {
        mutableStateOf(false)
    }

    Column {
        TopAppBar(
            title = {
                Text("AIT Todo")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor =
                MaterialTheme.colorScheme.secondaryContainer
            ),
            actions = {
                IconButton(onClick = {
                    todoListViewModel.clearAllTodos()
                }) {
                    Icon(Icons.Filled.Delete, null)
                }
                IconButton(onClick = {
                    summaryClickHandler(
                        todoListViewModel.getAllTodoNum(),
                        todoListViewModel.getImportantTodoNum()
                    )
                }) {
                    Icon(Icons.Filled.Info, null)
                }
                IconButton(onClick = {
                    showAddDialog = true
                }) {
                    Icon(Icons.Filled.Add, null)
                }
            })

        if (showAddDialog) {
            AddNewTodoForm(dialogDismiss = {showAddDialog = false})
        }

        if (todoListViewModel.getAllToDoList().isEmpty())
            Text(
                text = "Nothing Here ", textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxHeight()
            )
        else {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(todoListViewModel.getAllToDoList()) {
                    Flippable(
                        frontSide = {
                            TodoCard(todoItem = it,
                                onRemoveItem = {
                                    todoListViewModel.removeTodoItem(it)
                                },
                                onTodoCheckChange = { value ->
                                    todoListViewModel.changeTodoState(
                                        todoItem = it,
                                        value = value,
                                    )
                                })
                        },

                        backSide = {
                            TodoCardBack(
                                todoListViewModel = todoListViewModel,
                                todoItem = it
                            )
                        },

                        flipController = rememberFlipController(),

                        // Other optional parameters
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewTodoForm(todoListViewModel: TodoListViewModel = viewModel(),
    dialogDismiss: ()->Unit) {
    Dialog(onDismissRequest = dialogDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                var newTodoTitle by remember {
                    mutableStateOf("")
                }
                var newTodoDesc by remember {
                    mutableStateOf("")
                }
                var newTodoPriority by remember {
                    mutableStateOf(false)
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newTodoTitle,
                        label = { Text(text = "Title") },
                        onValueChange = {
                            newTodoTitle = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    OutlinedTextField(
                        value = newTodoDesc,
                        label = { Text(text = "Description") },
                        onValueChange = {
                            newTodoDesc = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding()
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = newTodoPriority,
                        onCheckedChange = {
                            newTodoPriority = it
                        },
                    )
                    Text(text = "Important")
                }
                Button(onClick = {
                    todoListViewModel.addTodoList(
                        TodoItem(
                            UUID.randomUUID().toString(),
                            newTodoTitle,
                            newTodoDesc,
                            Date(System.currentTimeMillis()).toString(),
                            if (newTodoPriority) TodoPriority.HIGH else TodoPriority.NORMAL,
                            false
                        )
                    )
                    dialogDismiss.invoke()
                }) {
                    Text(text = "Add Todo")
                }
            }
        }
    }
}


@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }

            Image(
                painter = painterResource(
                    todoItem.priority.getIcon()
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 5.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = todoItem.title,
                    textDecoration =
                    if (todoItem.isDone) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
                if (expanded) {
                    Text(
                        text = todoItem.description,
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )
                    Text(
                        text = todoItem.createDate,
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )
                }
            }


            Row {
                Checkbox(
                    checked = todoItem.isDone,
                    onCheckedChange = {
                        onTodoCheckChange(it)
                    },
                )
                Icon(

                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",

                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoCardBack(todoListViewModel: TodoListViewModel, todoItem: TodoItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text(
                text = todoItem.description
            )
        }
    }
}


@Preview
@Composable
fun TodoListScreenPreview() {
    TodoComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TodoListScreen(summaryClickHandler = { a, b -> })
        }
    }
}

@Preview
@Composable
fun TodoCardPreview() {
    TodoComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TodoCard(
                TodoItem(
                    "0", "Title", "Description", "2023",
                    TodoPriority.HIGH, false
                )
            )
        }
    }
}

