package hu.ait.roomgradedemo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDAO {
    @Query("SELECT * from grades")
    fun getAllGrades(): Flow<List<Grade>>

    @Query("SELECT * from grades ORDER BY studentname ASC")
    fun getAllGradesOrderByName(): Flow<List<Grade>>

    @Query("SELECT * from grades WHERE id = :id")
    fun getGrade(id: Int): Flow<Grade>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(grade: Grade)

    @Update
    suspend fun update(grade: Grade)

    @Delete
    suspend fun delete(grade: Grade)

    @Query("DELETE from grades")
    suspend fun deleteAllGrades()
}