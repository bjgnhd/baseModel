package com.gnhd.base.utils.permission

import android.content.Context
import com.gnhd.base.R
import com.hjq.permissions.Permission
import java.util.*

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XXPermissions
 * time   : 2022/06/11
 * desc   : 权限名称转换器
 */
object PermissionNameConvert {
    /**
     * 获取权限名称
     */
    fun getPermissionString(context: Context, permissions: List<String>): String {
        return listToString(permissionsToStrings(context, permissions))
    }

    /**
     * String 列表拼接成一个字符串
     */
    fun listToString(hints: List<String?>?): String {
        if (hints == null || hints.isEmpty()) {
            return ""
        }
        val builder = StringBuilder()
        for (text in hints) {
            if (builder.isEmpty()) {
                builder.append(text)
            } else {
                builder.append("、")
                    .append(text)
            }
        }
        return builder.toString()
    }

    /**
     * 将权限列表转换成对应名称列表
     */
    fun permissionsToStrings(context: Context, permissions: List<String>): List<String?> {
        val permissionNames: MutableList<String?> = ArrayList()
        for (permission in permissions) {
            when (permission) {
                Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE -> {
                    val hint = context.getString(R.string.common_permission_storage)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.CAMERA -> {
                    val hint = context.getString(R.string.common_permission_camera)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.RECORD_AUDIO -> {
                    val hint = context.getString(R.string.common_permission_microphone)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_BACKGROUND_LOCATION -> {
                    var hint: String = if (!permissions.contains(Permission.ACCESS_FINE_LOCATION) &&
                        !permissions.contains(Permission.ACCESS_COARSE_LOCATION)
                    ) {
                        context.getString(R.string.common_permission_location_background)
                    } else {
                        context.getString(R.string.common_permission_location)
                    }
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                Permission.READ_PHONE_STATE, Permission.CALL_PHONE, Permission.ADD_VOICEMAIL, Permission.USE_SIP, Permission.READ_PHONE_NUMBERS, Permission.ANSWER_PHONE_CALLS -> {
                    val hint = context.getString(R.string.common_permission_phone)
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint)
                    }
                }
                else -> {
                }
            }
        }
        return permissionNames
    }
}