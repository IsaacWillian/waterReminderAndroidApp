package com.waterreminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waterreminder.db.HistoryDao
import com.waterreminder.db.ReminderDao
import com.waterreminder.models.History
import com.waterreminder.models.Reminder

@Database(entities = arrayOf(Reminder::class, History::class),version = 3,exportSchema = false)
abstract class ReminderDataBase:RoomDatabase() {
    abstract val reminderDao: ReminderDao
    abstract val historyDao: HistoryDao
}