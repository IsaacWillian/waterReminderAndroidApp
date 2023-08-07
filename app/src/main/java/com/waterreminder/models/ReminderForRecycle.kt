package com.waterreminder.models

import com.waterreminder.models.Reminder

data class ReminderForRecycle(val reminder: Reminder, var marked:Boolean) {
}