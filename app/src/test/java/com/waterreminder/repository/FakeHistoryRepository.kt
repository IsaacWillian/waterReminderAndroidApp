package com.waterreminder.repository

import com.waterreminder.models.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHistoryRepository:HistoryRepository {
    val historys:LinkedHashMap<Int,History> = LinkedHashMap()

    override suspend fun saveHistory(history: History) {
        historys[history.id] = history
    }

    override suspend fun updateHistory(history: History) {
        historys[history.id] = history
    }

    override fun getHistory(day: Int, month: Int, year: Int): Flow<History?> {
        return flow {
            emit( historys.filter { it.value.day == day && it.value.month == month && it.value.year == year }.values.first())
        }
    }

    override fun getHistorysByMonth(month: Int, year: Int): Flow<List<History?>> {
        return flow {
            emit( historys.filter {it.value.month == month && it.value.year == year }.values.toList())
        }
    }

    fun addHistorys(newHistorys:List<History>){
        for (history in newHistorys){
            historys[history.id] = history
        }
    }
}