package com.waterreminder.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waterreminder.datastore.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    val mDataStoreRepository: DataStoreRepository
): ViewModel() {

    val redirectAction = MutableLiveData<RedirectAction>()
    fun checkFirstAccessAndRedirect() = viewModelScope.launch {
        val firstAccess = mDataStoreRepository.firstAccess.first()
        if (firstAccess) {
            redirectAction.postValue(RedirectAction.RedirectToOnboarding)
        } else {
            redirectAction.postValue(RedirectAction.RedirectToTodayFragment)
        }
    }
}

sealed class RedirectAction{
    object RedirectToOnboarding : RedirectAction()
    object RedirectToTodayFragment : RedirectAction()
}