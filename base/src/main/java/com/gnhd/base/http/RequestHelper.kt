package com.gnhd.base.http

import com.drake.net.request.BodyRequest
import com.drake.net.request.MediaConst
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/04/13
 *     desc   :
 * </pre>
 */

/**
 * json请求方式
 */
fun BodyRequest.gson(vararg body: Pair<String, Any?>) {
    this.body = Gson().toJson(body.toMap()).toRequestBody(MediaConst.JSON)
}
