package com.gnhd.base.utils

import com.drake.logcat.LogCat


/**
 * <pre>
 * @author : Trial
 * @time   : 2021/11/23
 * @desc   :
 * @version: 1.0
</pre> *
 */
object DLogUtil {
    init {
        LogCat.tag = "zts"
    }

    fun i(tag: String = "zts", msg: Any?) {
        LogCat.i(msg, tag)
    }

    fun i(msg: Any?) {
        LogCat.i(msg)
    }

    fun d(tag: String = "zts", msg: Any?) {
        LogCat.d(msg, tag)
    }

    fun d(msg: Any?) {
        LogCat.d(msg)
    }

    fun w(tag: String = "zts", msg: Any?) {
        LogCat.w(msg, tag)
    }

    fun w(msg: Any?) {
        LogCat.w(msg)
    }

    fun e(tag: String = "zts", msg: Any?) {
        LogCat.e(msg, tag)
    }

    fun e(msg: Any?) {
        LogCat.e(msg)
    }
}