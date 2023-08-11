package com.waterreminder.repository

import android.content.Context
import android.os.LocaleList
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader

class MotivationalPhraseRepositoryImpl(context: Context): MotivationalPhrasesRepository {
    val motivationalPhrases:Array<String> by lazy {
        val gson = GsonBuilder().create()
        gson.fromJson(json,Array<String>::class.java)
    }
    val json: String? by lazy {
        val locale = LocaleList.getDefault()
        val local = locale.get(0)
        val fileName = "motivational_phrases_${local.language}.json"
        try {
            context.assets.open(fileName).bufferedReader()
                .use(BufferedReader::readText)
        } catch (e:Exception){
            context.assets.open("motivational_phrases_en.json").bufferedReader()
                .use(BufferedReader::readText)
        }
    }


    override fun getMotivationalPhraseFlow() = flow {
        while(true){
            emit(motivationalPhrases.random())
            delay(20000L)
        }
    }

    override fun getMotivationalPhrase(): String {
        return motivationalPhrases.random()
    }
}