package com.waterreminder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waterreminder.datastore.DataStoreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val mDataStoreRepository: DataStoreRepository
): ViewModel() {



    fun isFirstAccess() = mDataStoreRepository.firstAccess


}