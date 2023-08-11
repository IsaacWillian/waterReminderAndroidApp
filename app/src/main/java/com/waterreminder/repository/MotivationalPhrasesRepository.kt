package com.waterreminder.repository

import kotlinx.coroutines.flow.Flow

interface MotivationalPhrasesRepository {

    fun getMotivationalPhraseFlow() : Flow<String>

    fun getMotivationalPhrase() : String
}