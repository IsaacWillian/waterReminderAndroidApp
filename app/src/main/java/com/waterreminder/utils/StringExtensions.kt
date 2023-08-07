package com.waterreminder.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.waterreminder.R

fun String.copyToClipBoard(context: Context){
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("DrinkNow",this)
    clipboard.setPrimaryClip(clip)
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
}