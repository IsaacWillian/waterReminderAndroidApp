package com.waterreminder.repository

import com.waterreminder.models.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun saveReminder(reminder: Reminder):Long
    suspend fun deleteReminder(reminder: Reminder)
    suspend fun getReminder(id:Int): Reminder
    val allReminders:Flow<List<Reminder>>
}