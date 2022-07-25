@file:JvmName("ResUtil")

package com.gnhd.base.utils

import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.gnhd.base.base.application


@JvmName("getColor")
fun getResColor(@ColorRes colorRes: Int) =
    ContextCompat.getColor(application, colorRes)


@JvmName("getDrawable")
fun getResDrawable(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(application, drawableRes)


@JvmName("getString")
fun getResString(@StringRes stringId: Int, vararg formatArgs: Any) =
    application.applicationContext.getString(stringId, *formatArgs)


@JvmName("getStringArray")
fun getResStringArray(@ArrayRes arrayId: Int): Array<String> =
    application.applicationContext.resources.getStringArray(arrayId)


@JvmName("getIntArray")
fun getResIntArray(@ArrayRes arrayId: Int) =
    application.applicationContext.resources.getIntArray(arrayId)
