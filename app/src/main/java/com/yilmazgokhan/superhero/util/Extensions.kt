package com.yilmazgokhan.superhero.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils
import com.yilmazgokhan.superhero.R

/**
 * Show alert dialog
 */
fun Context.showMessage(
    message: String,
    onPositive: (() -> Unit)? = null
) {
    MaterialDialog(this).show {
        cancelable(false)
        cancelOnTouchOutside(false)
        message(text = message)
        positiveButton(R.string.dialog_ok) {
            onPositive?.invoke()
        }
    }
}

/**
 * Load Image with Coil
 */
fun ImageView.loadImage(url: String?) {
    if (url != null) {
        this.load(url) {
            placeholder(PlaceHolderProgress(context))
            error(PlaceHolderProgress(context))
            crossfade(false)
        }
    }
}

/**
 * Set background color via stats value
 */
@SuppressLint("ResourceType")
fun TextView.statsValue(value: Int?) {
    if (value == null) {
        this.text = "0"
        return
    }
    this.text = value.toString()
    when (value) {
        in 0..25 -> this.setBackgroundColor(ColorUtils.getColor(R.color.level_1))
        in 26..50 -> this.setBackgroundColor(ColorUtils.getColor(R.color.level_2))
        in 51..75 -> this.setBackgroundColor(ColorUtils.getColor(R.color.level_3))
        in 76..101 -> this.setBackgroundColor(ColorUtils.getColor(R.color.level_4))
    }
}

/**
 * Parse value and format it
 */
fun TextView.textWithFormat(value: String?) {
    if (value == null || StringUtils.equals(value, "-") || StringUtils.equals(value, ""))
        this.text = "Unknown"
    else
        this.text = value
}

/**
 * Parse heroes' height value and format it
 */
fun prepareHeight(value: String): String {
    if (value == "[-, 0 cm]")
        return "Unknown"
    var height = ""
    height = value.substring(1)
    height = height.substring(0, height.length - 1)
    return height
}

/**
 * Parse heroes' weight value and format it
 */
fun prepareWeight(value: String): String {
    if (value == "[- lb, 0 kg]")
        return "Unknown"
    var height = ""
    height = value.substring(1)
    height = height.substring(0, height.length - 1)
    return height
}

/**
 * Relatives values are separated by ";"
 */
fun prepareRelativesList(value: String?): List<String>? {
    try {
        if (value == null || StringUtils.equals(value, "-") || StringUtils.equals(value, ""))
            return null
        return when {
            value.contains(";") -> {
                value.split(";").map { it.trim() }
            }
            else -> listOf(value)
        }
    } catch (e: Exception) {
        return null
    }
}

/**
 * Affiliations values are separated by ","
 */
fun prepareAffiliationList(value: String?): List<String>? {
    try {
        if (value == null || StringUtils.equals(value, "-") || StringUtils.equals(value, ""))
            return null
        return when {
            value.contains(",") -> {
                value.split(",").map { it.trim() }
            }
            else -> listOf(value)
        }
    } catch (e: Exception) {
        return null
    }
}