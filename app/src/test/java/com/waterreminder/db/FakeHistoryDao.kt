package com.waterreminder.db

import com.waterreminder.models.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHistoryDao: HistoryDao {
    var historys:LinkedHashMap<Int, History> = LinkedHashMap()

    override fun saveNewHistory(history: History) {
        historys[history.id] = history
    }

    override fun updateHistory(history: History) {
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
        for(history in newHistorys){
            historys[history.id] = history
        }
    }

}