package com.waterreminder.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.waterreminder.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert
    fun saveReminder(reminder: Reminder):Long

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders ORDER BY hour,minutes ASC ")
    fun getAllReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE id=:id")
    fun getReminder(id:Int): Reminder

}