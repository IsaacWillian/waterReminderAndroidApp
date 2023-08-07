package com.waterreminder.repository

import com.waterreminder.db.ReminderDao
import com.waterreminder.models.Reminder
import com.waterreminder.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(private val reminderDao: ReminderDao): ReminderRepository {

    override val allReminders : Flow<List<Reminder>>
        get() = reminderDao.getAllReminders()

    override suspend fun saveReminder(reminder: Reminder):Long{
        return reminderDao.saveReminder(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }

    override suspend fun getReminder(id:Int): Reminder {
        return reminderDao.getReminder(id)
    }
}