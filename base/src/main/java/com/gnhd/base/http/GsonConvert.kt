package com.gnhd.base.http

import android.text.TextUtils
import com.gnhd.base.utils.DToastUtils
import com.hjq.gson.factory.GsonFactory
import org.json.JSONObject
import java.lang.reflect.Type


/**
 * <pre>
 *     @author : Trial
 *     @time   : 11/18/21
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
class GsonConvert : JSONConvert(code = "code", message = "msg", success = 0) {
    // 获取单例的 Gson 对象（已处理容错）
    private var gson = GsonFactory.getSingletonGson()

    override fun <S> String.parseBody(succeed: Type): S? {
        val code = JSONObject(this).getInt("code")
        return if (0 == code) {
            val data = JSONObject(this).getString("data")
            gson.fromJson(data, succeed)
        } else {
            val msg = JSONObject(this).getString("msg")
            if (!TextUtils.isEmpty(msg)) {
                DToastUtils.show(msg)
            }
            null
        }
    }
}