package com.gnhd.base.http

import com.drake.net.convert.NetConverter
import com.drake.net.exception.ConvertException
import com.drake.net.exception.RequestParamsException
import com.drake.net.exception.ServerResponseException
import com.drake.net.request.kType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Response
import org.json.JSONException
import java.lang.reflect.Type
import kotlin.reflect.KType

/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/11/22
 *     desc   :
 * </pre>
 */
open class SerializationForCodeConverter(
    val success: Int = 0,
    val code: String = "code",
    val message: String = "msg"
) : NetConverter {

    companion object {
        val jsonDecoder = Json {
            ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
            coerceInputValues = true // 如果Json字段是Null则使用数据类字段默认值
        }
    }

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bodyString = response.body?.string() ?: return null
                    val kType = response.request.kType
                        ?: throw ConvertException(response, "Request does not contain KType")
                    return try {
                        bodyString.parseBody<R>(kType)

                    } catch (e: JSONException) { // 固定格式JSON分析失败直接解析JSON
                        bodyString.parseBody<R>(kType)
                    }
                }
                code in 400..499 -> throw RequestParamsException(
                    response,
                    code.toString()
                ) // 请求参数错误
                code >= 500 -> throw ServerResponseException(response, code.toString()) // 服务器异常错误
                else -> throw ConvertException(response)
            }
        }
    }

     fun <R> String.parseBody(succeed: KType): R? {
        return jsonDecoder.decodeFromString(Json.serializersModule.serializer(succeed), this) as R
    }
}