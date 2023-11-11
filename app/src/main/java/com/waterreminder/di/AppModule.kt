package com.waterreminder

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.waterreminder.datastore.DataStoreRepository
import com.waterreminder.datastore.DataStoreRepositoryImpl
import com.waterreminder.db.HistoryDao
import com.waterreminder.db.ReminderDao
import com.waterreminder.db.ReminderDataBase
import com.waterreminder.repository.HistoryRepository
import com.waterreminder.repository.HistoryRepositoryImpl
import com.waterreminder.repository.MotivationalPhraseRepositoryImpl
import com.waterreminder.repository.MotivationalPhrasesRepository
import com.waterreminder.repository.ReminderRepository
import com.waterreminder.repository.ReminderRepositoryImpl
import com.waterreminder.ui.HistoryViewModel
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.utils.AlarmUtils
import com.waterreminder.utils.NotificationUtils
import com.waterreminder.utils.PermissionsUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val userDb = module {
    fun provideDataBase(application: Application): ReminderDataBase {
        return Room.databaseBuilder(application, ReminderDataBase::class.java,"reminder_database").build()
    }

    fun provideReminderDao(dataBase: ReminderDataBase): ReminderDao {
        return dataBase.reminderDao
    }

    fun provideHistoryDao(dataBase: ReminderDataBase): HistoryDao {
        return dataBase.historyDao
    }

    fun provideDataStore(context: Context) = PreferenceDataStoreFactory.create(produceFile = {context.preferencesDataStoreFile("UserPreferences")})


    single<DataStoreRepository>{ DataStoreRepositoryImpl(get())}
    single{ provideDataStore(androidContext())}
    single{ provideDataBase(androidApplication())    }
    single{ provideHistoryDao(get())}
    single{ provideReminderDao(get())}
    single<ReminderRepository>{ ReminderRepositoryImpl(get()) }
    single<HistoryRepository>{ HistoryRepositoryImpl(get()) }
    single<MotivationalPhrasesRepository>{ MotivationalPhraseRepositoryImpl(androidContext())}
    single{ NotificationUtils(androidContext(),get()) }
    single { AlarmUtils(androidContext()) }
    single { PermissionsUtils(androidContext())}

    viewModel{ ReminderViewModel(get(),get(),get(),get(),get()) }
    viewModel{ HistoryViewModel(get(),get()) }

}