package com.yilmazgokhan.superhero.util

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.yilmazgokhan.superhero.R

/**
 * Loading placeholder for coil
 */
class PlaceHolderProgress constructor(context: Context) : CircularProgressDrawable(context) {
    init {
        initialize(context)
    }

    private fun initialize(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            colorFilter = BlendModeColorFilter(
                ContextCompat.getColor(context, R.color.black),
                BlendMode.SRC_IN
            )
        } else {
            setColorFilter(
                ContextCompat.getColor(context, R.color.black),
                PorterDuff.Mode.SRC_IN
            )
        }
        this.strokeWidth = 5F
        this.centerRadius = 30f
        this.start()
    }
}