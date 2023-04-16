package hu.ait.roomgradedemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.roomgradedemo.data.Grade
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun GradeScreen(
    gradeViewModel: GradeViewModel = viewModel(factory = GradeViewModel.factory)

) {
    val coroutineScope = rememberCoroutineScope()

    val gradeList by gradeViewModel.getAllGrades().collectAsState(emptyList())

    var studentName by rememberSaveable { mutableStateOf("") }
    var grade by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            label = { Text(text = "Student name") },
            value = studentName,
            onValueChange = {
                studentName = it
            })
        OutlinedTextField(
            label = { Text(text = "Grade") },
            value = grade,
            onValueChange = {
                grade = it
            })

        Button(onClick = {
            gradeViewModel.deleteAllGrades2()
        }) {
            Text(text = "Delete all")
        }

        Button(onClick = {
            coroutineScope.launch {

                gradeViewModel.addGrade(
                    Grade(
                        studentName = studentName,
                        grade = grade
                    )
                )
            }
        }) {
            Text(text = "Save")
        }

        LazyColumn {
            items(gradeList) {
                Text(
                    text = "${it.studentName} - ${it.grade}",
                    fontSize = 22.sp
                )
            }
        }
    }
}