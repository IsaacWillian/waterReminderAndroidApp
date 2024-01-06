package com.waterreminder.ui

import com.waterreminder.datastore.FakeDataStoreRepository
import com.waterreminder.models.History
import com.waterreminder.repository.FakeHistoryRepository
import com.waterreminder.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.waterreminder.ui.viewModels.HistoryViewModel
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest() {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mHistoryRepository: FakeHistoryRepository
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var mDataStoreRepository: FakeDataStoreRepository

    val history1 = History(1,14,12,2022, mutableListOf(1,2),300,2000)
    val history2 = History(2,16,11,2022, mutableListOf(1),400,2000)
    val history3 = History(3,15,12,2022, mutableListOf(1,2),1000,2000)
    val historys = listOf(history1,history2,history3)
    val historysSameMonth = listOf(history1,history3)


    @Before
    fun setup(){
        mHistoryRepository = FakeHistoryRepository()
        mDataStoreRepository = FakeDataStoreRepository()
        mHistoryViewModel = HistoryViewModel(mHistoryRepository,mDataStoreRepository)



    }


    @Test
    fun `when call updateHistory historysByMonth should be updated`() =  runTest {
        // Given a list of historys
        mHistoryRepository.addHistorys(historys)

        // When call updateHistorys
        mHistoryViewModel.updateHistories()
        val result = mHistoryViewModel.historysByMonth.getOrAwaitValue()


        // Then historysByMonth shoud be equal historysSameMonth
        assertThat(result).isEqualTo(historysSameMonth)

    }
}