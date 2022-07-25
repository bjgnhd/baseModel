package com.gnhd.base.utils

import android.text.TextUtils
import android.widget.EditText

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2022/05/13
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
object NullOrEmptyUtils {

    @JvmStatic
    fun isNullOrEmpty(str: String?): Boolean {
        return TextUtils.isEmpty(str)
    }
    @JvmStatic
    fun isNullOrEmpty(str: Double): Boolean {
        return str <= 0
    }
    @JvmStatic
    fun isNullOrEmpty(str: List<Any>): Boolean {
        val empty = str.isEmpty()
        return str.isEmpty()
    }

    /**
     * 判断edittext是否null
     */
    @JvmStatic
    fun checkEditText(editText: EditText?): String {
        return if (editText != null && editText.text != null && editText.text.toString()
                .trim { it <= ' ' } != ""
        ) {
            editText.text.toString().trim { it <= ' ' }
        } else {
            ""
        }
    }
}