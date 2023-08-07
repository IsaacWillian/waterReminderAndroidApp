package com.waterreminder.repository

import com.waterreminder.db.FakeHistoryDao
import com.waterreminder.db.HistoryDao
import com.waterreminder.models.History
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryRepositoryImplTest{
    val history1 = History(1,14,12,2022, mutableListOf(1,2),300,2000)
    val history2 = History(2,16,11,2022, mutableListOf(1),400,2000)
    val history3 = History(3,15,12,2022, mutableListOf(1,2),1000,2000)
    val historys = listOf(history1,history2,history3)
    val historysSameMonth = listOf(history1,history3)

    private lateinit var mFakeHistoryDao: FakeHistoryDao
    private lateinit var mHistoryRepositoryImpl: HistoryRepository

    @Before
    fun setup(){
        mFakeHistoryDao = FakeHistoryDao()
        mHistoryRepositoryImpl = HistoryRepositoryImpl(mFakeHistoryDao)
    }

    @Test
    fun `with historys getHistorysByMonth should return 2 historys`() = runTest {
        //Given a list with historys
        mFakeHistoryDao.addHistorys(historys)

        // When call getHistorysByMonth
        val result = mHistoryRepositoryImpl.getHistorysByMonth(12,2022).first()

        // Then return a list equal historysSameMonth
        assertThat(result).isEqualTo(historysSameMonth)
    }

    @Test
    fun `with historys getHistory should return a correct history`() = runTest{
        //Given a list with historys
        mFakeHistoryDao.addHistorys(historys)

        // When call getHistory
        val result = mHistoryRepositoryImpl.getHistory(history1.day,history1.month,history1.year).first()

        // Then return a correct history
        assertThat(result).isEqualTo(history1)
    }

    @Test
    fun `with history1 updateHistory should update history correct`() = runTest {
        // Given a  history and a new history with same index
        val newHistory = history1.copy(waterOfDay = 500)

        // When call updateHistory
        mFakeHistoryDao.updateHistory(newHistory)

        // Then historys retured with same date of history1 should be equal a newHistory
        assertThat(mHistoryRepositoryImpl.getHistory(history1.day,history1.month,history1.year).first()).isEqualTo(newHistory)
    }

    @Test
    fun `saveHistory should saves correct`() = runTest {
        // When call saveHistory
        mHistoryRepositoryImpl.saveHistory(history3)

        // Then return the history saved
        assertThat(mHistoryRepositoryImpl.getHistory(history3.day,history3.month,history3.year).first()).isEqualTo(history3)
    }

}