package com.waterreminder.db

import androidx.room.*
import com.waterreminder.models.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNewHistory(history: History)

    @Update
    fun updateHistory(history: History)

    @Query("SELECT * FROM history WHERE day=:day AND month=:month AND year=:year")
    fun getHistory(day: Int,month:Int,year:Int): Flow<History?>

    @Query("SELECT * FROM history WHERE month=:month AND year=:year ORDER BY day ASC")
    fun getHistorysByMonth(month:Int,year:Int):Flow<List<History?>>

}