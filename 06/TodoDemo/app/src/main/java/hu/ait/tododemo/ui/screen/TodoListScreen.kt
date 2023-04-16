package hu.ait.tododemo.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.tododemo.data.TodoItem
import hu.ait.tododemo.data.TodoPriority
import java.util.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import hu.ait.tododemo.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.canopas.lib.showcase.ShowcaseStyle
import hu.ait.tododemo.preferences.MySettings
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.canopas.lib.showcase.IntroShowCaseScaffold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoListViewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.factory),
    navController: NavController
) {
    val context = LocalContext.current
    val store = MySettings(context)
    val isFirstStart = store.isFirstStart.collectAsState(initial = true)

    val todoList by todoListViewModel.getAllToDoList().collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()

    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var todoToEdit: TodoItem? by rememberSaveable {
        mutableStateOf(null)
    }

    IntroShowCaseScaffold(showIntroShowCase = true,
        onShowCaseCompleted = {
            coroutineScope.launch {
                store.saveFirstStart(false)
            }
        }) {


        Column(
        ) {
            var topAppBarExpanded by remember { mutableStateOf(false) }

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
                        coroutineScope.launch {
                            val allTodo = todoListViewModel.getAllTodoNum()
                            val importantTodo = todoListViewModel.getImportantTodoNum()
                            navController.navigate(
                                "summary/${allTodo}/${importantTodo}"
                            )
                        }
                    }) {
                        Icon(Icons.Filled.Info, null)
                    }
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.introShowCaseTarget(
                            index = 0,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = Color(0xFF1C0A00), // specify color of background
                                backgroundAlpha = 0.5f, // specify transparency of background
                                targetCircleColor = Color.White // specify color of target circle
                            ),
                            // specify the content to show to introduce app feature
                            content = {
                                Column {
                                    Text(
                                        text = "Add items",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Click here add new items",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        ),
                    ) {
                        Icon(Icons.Filled.Add, null)
                    }

                    IconButton(
                        modifier = Modifier.introShowCaseTarget(
                            index = 1,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = Color(0xFF1C0A00), // specify color of background
                                backgroundAlpha = 0.5f, // specify transparency of background
                                targetCircleColor = Color.White // specify color of target circle
                            ),
                            // specify the content to show to introduce app feature
                            content = {
                                Column {
                                    Text(
                                        text = "Clear items",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Click here to delete items",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }),
                        onClick = {
                            todoListViewModel.clearAllTodos()
                        }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                    IconButton(
                        onClick = { topAppBarExpanded = !topAppBarExpanded }
                    ) { Icon(Icons.Filled.MoreVert, contentDescription = null) }
                    DropdownMenu(
                        expanded = topAppBarExpanded,
                        onDismissRequest = { topAppBarExpanded = false }) {
                        DropdownMenuItem(onClick = { },
                            text = { Text(text = "Show All") })
                        DropdownMenuItem(onClick = { },
                            text = { Text(text = "Show Important") })
                    }

                })

            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                if (showAddDialog) {
                    AddNewTodoForm(
                        onDialogClose = {
                            showAddDialog = false
                            todoToEdit = null
                        },
                        todoToEdit = todoToEdit
                    )
                }

                LazyColumn() {
                    items(todoList) { todoItem ->
                        TodoCard(todoItem = todoItem,
                            onTodoCheckChange = { checked ->
                                todoListViewModel.changeTodoState(todoItem, checked)
                            },
                            onRemoveItem = {
                                todoListViewModel.removeTodoItem(todoItem)
                            },
                            onEditItem = {
                                showAddDialog = true
                                todoToEdit = todoItem
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTodoForm(
    todoListViewModel: TodoListViewModel = viewModel(),
    onDialogClose: () -> Unit = {},
    todoToEdit: TodoItem?
) {
    var newTodoTitle by remember { mutableStateOf(todoToEdit?.title ?: "") }
    var newTodoDesc by remember { mutableStateOf(todoToEdit?.description ?: "") }
    var newTodoPriority by remember { mutableStateOf(todoToEdit?.isDone ?: false) }

    Dialog(onDismissRequest = onDialogClose) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {

            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(value = newTodoTitle,
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Todo title") },
                        onValueChange = {
                            newTodoTitle = it
                        }
                    )
                    OutlinedTextField(value = newTodoDesc,
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Description") },
                        onValueChange = {
                            newTodoDesc = it
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = newTodoPriority, onCheckedChange = {
                        newTodoPriority = it
                    })
                    Text(text = "Important")
                }

                Button(onClick = {
                    if (todoToEdit == null) {

                        todoListViewModel.addTodoList(
                            TodoItem(
                                title = newTodoTitle,
                                description = newTodoDesc,
                                createDate = Date(System.currentTimeMillis()).toString(),
                                priority = if (newTodoPriority) TodoPriority.HIGH else TodoPriority.NORMAL,
                                isDone = false
                            )
                        )
                    } else {
                        var todoEdited = todoToEdit.copy(
                            title = newTodoTitle,
                            description = newTodoDesc,
                            priority = if (newTodoPriority) TodoPriority.HIGH else TodoPriority.NORMAL,
                        )

                        todoListViewModel.editTodoItem(
                            todoEdited
                        )
                    }

                    onDialogClose()
                }) {
                    Text(text = "Add")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (TodoItem) -> Unit = {}
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
        var expanded by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = todoItem.priority.getIcon()),
                    contentDescription = "Priority",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
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
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = todoItem.isDone,
                        onCheckedChange = {
                            onTodoCheckChange(it)
                        },
                    )
                    IconButton(onClick = {
                        onEditItem(todoItem)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
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
                            imageVector = if (expanded)
                                Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = if (expanded) {
                                "Less"
                            } else {
                                "More"
                            }
                        )
                    }


                }
            }

            if (expanded) {

                Text(text = todoItem.description)
                Text(
                    text = todoItem.createDate,
                    style = TextStyle(fontSize = 12.sp)
                )

            }


        }
    }
}

