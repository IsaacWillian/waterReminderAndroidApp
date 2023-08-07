package com.waterreminder.datastore

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeDataStoreRepository() : DataStoreRepository {
    val preferences:LinkedHashMap<String,Any> = LinkedHashMap()

    override val userNameFlow: Flow<String>
        get() = flow {
            emit(preferences["userName"] as String)
        }

    override val goalOfDayFlow: Flow<Int>
        get() = flow{
            emit(preferences["goalOfDay"] as Int)
        }

    override val firstAccess: Flow<Boolean>
        get() = flow{
            emit(preferences["firstAccess"] as Boolean)
        }

    override suspend fun saveUserName(userName: String) {
        preferences["userName"] = userName
    }

    override suspend fun saveGoalOfDay(goalOfDay: Int) {
        preferences["goalOfDay"] = goalOfDay
    }

    override suspend fun saveFirstAccess(firstAccess: Boolean) {
        preferences["firstAccess"] = firstAccess
    }
}