package com.waterreminder.db

import com.waterreminder.models.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeReminderDao: ReminderDao {
    var reminders:LinkedHashMap<Long,Reminder> = LinkedHashMap()

    override fun saveReminder(reminder: Reminder): Long {
        reminders[reminder.id] = reminder
        return reminder.id
    }

    override fun deleteReminder(reminder: Reminder) {
        reminders.remove(reminder.id)
    }

    override fun getAllReminders(): Flow<List<Reminder>> {
        return flow {  emit(reminders.values.toList()) }
    }

    override fun getReminder(id: Int): Reminder {
        return reminders[id.toLong()]!!

    }

    fun addReminders(newReminders:List<Reminder>){
        for (reminder in newReminders){
            reminders[reminder.id] = reminder
        }
    }
}