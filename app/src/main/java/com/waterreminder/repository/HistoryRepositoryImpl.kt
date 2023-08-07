package com.waterreminder.repository

import com.waterreminder.db.HistoryDao
import com.waterreminder.models.History
import com.waterreminder.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(val historyDao: HistoryDao): HistoryRepository {
    override suspend fun saveHistory(history: History) {
        historyDao.saveNewHistory(history)
    }

    override suspend fun updateHistory(history: History) {
        historyDao.updateHistory(history)
    }

    override fun getHistory(day:Int,month:Int,year:Int) = historyDao.getHistory(day,month,year)

    override fun getHistorysByMonth(month: Int, year: Int): Flow<List<History?>> = historyDao.getHistorysByMonth(month,year)
}