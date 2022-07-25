package com.gnhd.base.listener

import com.luck.picture.lib.entity.LocalMedia

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/05/31
 *     desc   :
 * </pre>
 */
interface OnResultListener{
    /**
     * return LocalMedia result
     *
     * @param result
     */
    fun onResult(result: ArrayList<LocalMedia>?)

    /**
     * Cancel
     */
    fun onCancel()
}