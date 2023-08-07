package com.waterreminder.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.waterreminder.db.Converters


@Entity(tableName = "history")
@TypeConverters(Converters::class)
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val day: Int,
    val month: Int,
    val year: Int,
    val markedReminders: MutableList<Long>,
    var waterOfDay:Int,
    var goalOfDay:Int
)