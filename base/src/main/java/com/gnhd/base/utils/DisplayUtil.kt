@file:JvmName("DisUtil")
package com.gnhd.base.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/24
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
private val displayMetrics = Resources.getSystem().displayMetrics

val Float.dp2px: Float
    @JvmName("dp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)

val Int.dp2px: Int
    @JvmName("dp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
        .toInt()

val Number.px2dp: Int
    @JvmName("px2dp")
    get() = (this.toFloat() / displayMetrics.density).toInt()


val SCREEN_WIDTH: Int
    @JvmName("SCREEN_WIDTH")
    get() = displayMetrics.widthPixels

val SCREEN_HEIGHT: Int
    @JvmName("SCREEN_HEIGHT")
    get() = displayMetrics.heightPixels