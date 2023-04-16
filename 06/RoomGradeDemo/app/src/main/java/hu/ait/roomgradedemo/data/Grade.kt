package hu.ait.roomgradedemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "grades")
data class Grade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "studentname") var studentName: String,
    @ColumnInfo(name = "grade") var grade: String,
)