package com.waterreminder.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.waterreminder.databinding.DrinkWaterButtonBinding

class DrinkWaterButton @JvmOverloads constructor(context: Context,attrs:AttributeSet? = null, defStyleAttr:Int = 0) : ConstraintLayout(context,attrs,defStyleAttr) {
        private val waterText : TextView
        private var alphaAnimation:AlphaAnimation
        private var translateAnimation: TranslateAnimation
        private var animationSet:AnimationSet

        private val binding: DrinkWaterButtonBinding =
                DrinkWaterButtonBinding.inflate(LayoutInflater.from(context),this,true)


        init{
                waterText = binding.text
                alphaAnimation = AlphaAnimation(1F,0F).apply {
                        duration = 300
                }
                translateAnimation = TranslateAnimation(waterText.x,waterText.x,waterText.y,waterText.y-100F).apply {
                        duration = 400
                }
                animationSet = AnimationSet(true).apply {
                        addAnimation(translateAnimation)
                        addAnimation(alphaAnimation)
                }
        }

        fun setLayout(mlOfWater:String,newImage:Drawable){
                waterText.text = mlOfWater + "ml"
        }
        fun drinkAnimation(){
                waterText.startAnimation(animationSet)

        }
}