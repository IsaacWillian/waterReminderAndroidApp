package com.waterreminder.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object Analytics {

    val firebaseAnalytics = Firebase.analytics

    fun sendEvent(eventName:String,params:Map<String,String>? = null){
        firebaseAnalytics.logEvent(eventName){
            params?.let {
                for (p in params) {
                    param(p.key, p.value)
                }
            }
        }
    }
}