package com.gnhd.base.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * <pre>
 * @author : Trial
 * @time   : 2/1/21
 * @desc   : dp、px工具类
 * @version: 1.0
</pre> *
 */
object PixelUtil {
    /**
     * dp-->px
     *
     * @param dp
     * @return
     */
    @JvmStatic
    fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    fun dp2pxF(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics
        )
    }

    fun sp2px(sp: Float): Float {
        val scale = Resources.getSystem().displayMetrics.scaledDensity
        return sp * scale
    }
}