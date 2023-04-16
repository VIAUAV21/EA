package hu.ait.tododemo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {
    @Query("SELECT * from todotable")
    fun getAllTodos(): Flow<List<TodoItem>>

    @Query("SELECT COUNT(*) from todotable")
    suspend fun getTodosNum(): Int

    @Query("""SELECT COUNT(*) from todotable WHERE todopriority="HIGH"""")
    suspend fun getImportantTodosNum(): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)

    @Query("DELETE from todotable")
    suspend fun deleteAllTodos()
}