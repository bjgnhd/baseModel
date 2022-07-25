package com.gnhd.base.utils.permission

import android.Manifest

/**
 * <pre>
 * @author : Trial
 * @time   : 11/23/21
 * @desc   :
 * @version: 1.0
</pre> *
 */
object PermissionConstants {

    private val perMap = mutableMapOf<String, String>()

    const val STORE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val PHONE_STATE = Manifest.permission.READ_PHONE_STATE
    const val CAMERA = Manifest.permission.CAMERA
    const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
    const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    const val CALL_PHONE = Manifest.permission.CALL_PHONE
    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

    init {
        perMap[STORE] = "存储"
        perMap[PHONE_STATE] = "电话"
        perMap[CAMERA] = "相机"
        perMap[RECORD_AUDIO] = "麦克风"
        perMap[READ_CONTACTS] = "通讯录"
        perMap[CALL_PHONE] = "电话"
        perMap[ACCESS_COARSE_LOCATION] = "模糊定位"
        perMap[ACCESS_FINE_LOCATION] = "精确定位"
    }

    fun getPermissionName(permission: String): String {
        return perMap[permission] ?: ""
    }

}