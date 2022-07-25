package com.gnhd.base.utils

import com.drake.tooltip.longToast
import com.drake.tooltip.toast


/**
 * <pre>
 *     @author : Trial
 *     @time   : 11/23/21
 *     @desc   : ToastUtils的包装
 *     @version: 1.0
 * </pre>
 */
object DToastUtils {

    fun show(string: String) {
        if (string.isNotEmpty()) {
            toast(string)
        }
    }

    fun show(strRes: Int) {
        toast(getResString(strRes))
    }

    fun showLong(string: String) {
        if (string.isNotEmpty()) {
            longToast(string)
        }
    }

}