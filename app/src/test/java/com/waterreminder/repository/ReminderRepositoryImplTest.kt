package com.waterreminder.repository

import com.waterreminder.db.FakeReminderDao
import com.waterreminder.db.ReminderDao
import com.waterreminder.models.Reminder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
class ReminderRepositoryImplTest{
    val reminder1 = Reminder(1,12,30)
    val reminder2 = Reminder(2,13,30)
    val reminder3 = Reminder(3,14,30)
    val reminders = listOf<Reminder>(reminder1,reminder2,reminder3)
    private lateinit var mReminderDao: FakeReminderDao
    private lateinit var mReminderRepository: ReminderRepository

    @Before
    fun setup(){
        mReminderDao = FakeReminderDao()
        mReminderRepository = ReminderRepositoryImpl(mReminderDao)
    }


    @Test
    fun `with reminders getAllReminders return list equals reminders`() = runTest {
        // Given a some tasks in repository
        mReminderDao.addReminders(reminders)

        // When call get all Reminders
        val result = mReminderRepository.allReminders.first()

        //  Then result should be equal reminders list
        assertThat(result).isEqualTo(reminders)

    }

    @Test
    fun `without reminders getAllReminders returns emptyList`() = runTest {
        // When call get all Reminders
        val result = mReminderRepository.allReminders.first()

        //  Then result should be a empty list
        assertThat(result).isEqualTo(emptyList<Reminder>())

    }

    @Test
    fun `saveReminder return correct id`() = runTest {
        // When call save reminder
        val result = mReminderRepository.saveReminder(reminder1)

        //  Then result should be equal reminder id
        assertThat(result).isEqualTo(reminder1.id)
    }

    @Test
    fun `saveReminder and allReminder should return a list with reminder saved`() = runTest {
        // When call save reminder
        mReminderRepository.saveReminder(reminder1)

        //  Then allReminder should be equal a reminder1
        assertThat(mReminderRepository.allReminders.first()).isEqualTo(listOf(reminder1))
    }

    @Test
    fun `deleteReminder and allReminder should return a list without a reminder`() = runTest {
        // Given a some tasks in repository
        mReminderDao.addReminders(reminders)

        // When call deleteReminder
        mReminderRepository.deleteReminder(reminder1)

        //  Then result should be equal reminders list without reminder deleted
        assertThat(mReminderRepository.allReminders.first()).isEqualTo(listOf(reminder2,reminder3))
    }

    @Test
    fun `getReminder should return correct reminder`() = runTest {
        // Given a some tasks in repository
        mReminderDao.addReminders(reminders)

        // When call getReminder
        val result = mReminderRepository.getReminder(reminder1.id.toInt())

        //  Then result should be equal reminders list without reminder deleted
        assertThat(result).isEqualTo(reminder1)
    }
















}