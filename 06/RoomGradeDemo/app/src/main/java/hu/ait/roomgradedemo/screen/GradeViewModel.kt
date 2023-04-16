package hu.ait.roomgradedemo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.ait.roomgradedemo.MainApplication
import hu.ait.roomgradedemo.data.Grade
import hu.ait.roomgradedemo.data.GradeDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GradeViewModel(
    private val gradeDAO: GradeDAO) : ViewModel() {

    fun getAllGrades() : Flow<List<Grade>> {
        return gradeDAO.getAllGrades()
    }

    suspend fun addGrade(grade: Grade) {
        gradeDAO.insert(grade)
    }

    suspend fun deleteAllGrades() {
        gradeDAO.deleteAllGrades()
    }

    fun deleteAllGrades2() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                gradeDAO.deleteAllGrades()
            }
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[
                        ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                          as MainApplication)
                GradeViewModel(gradeDAO = application.database.gradeDao())
            }
        }
    }
}