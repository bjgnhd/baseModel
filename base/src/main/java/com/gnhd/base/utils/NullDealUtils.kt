package com.gnhd.base.utils

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/10/10
 *     desc   :
 * </pre>
 */
object NullDealUtils {
    fun String?.dealNullOrEmpty(): String {
        return if (this.isNullOrEmpty()) {
            "--"
        } else {
            this
        }
    }
}