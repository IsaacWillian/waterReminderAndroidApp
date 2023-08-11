package com.waterreminder.utils

import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun View.gone() = run { this.visibility = View.GONE }
fun View.visible() = run { this.visibility = View.VISIBLE }
fun View.invisible() = run { this.visibility = View.INVISIBLE }

fun View.fadeOut() {
    this.animate().alpha(0F).apply {
        duration= 300L
    }.withEndAction {
        this.invisible()
        this.alpha = 0F
    }
}

fun View.fadeIn() {
    this.alpha = 0F
    this.visible()
    this.animate().alpha(1F).apply {
        duration= 500L
    }.withEndAction {
        alpha = 1F
    }
}

fun TextView.changeTextWithFadeOutFadeIn(text:String){
    GlobalScope.launch(Dispatchers.Main) {
        this@changeTextWithFadeOutFadeIn.fadeOut()
        delay(300)
        this@changeTextWithFadeOutFadeIn.text = text
        delay(100)
        this@changeTextWithFadeOutFadeIn.fadeIn()
    }

}