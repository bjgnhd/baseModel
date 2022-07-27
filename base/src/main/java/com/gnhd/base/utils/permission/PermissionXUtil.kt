package com.gnhd.base.utils.permission

import com.gnhd.base.manager.ActivityManager
import com.gnhd.base.utils.DToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions

/**
 * <pre>
 * author : Administrator
 * time   : 2022/07/27
 * desc   :
</pre> *
 */
object PermissionXUtil {

    /**
     * 判断一个或多个权限是否全部授予了
     */
    fun checkIsGranted(vararg permissions: String): Boolean {
        return XXPermissions.isGranted(ActivityManager.getInstance().getTopActivity(), permissions)
    }

    fun checkPermission(onRequestCallback: () -> Unit = {}, vararg permissions: String) {
        XXPermissions.with(ActivityManager.getInstance().getTopActivity())
            // 申请单个权限
            .permission(permissions)
            // 设置权限请求拦截器（局部设置）
            .interceptor(PermissionInterceptor())
            .request(object : OnPermissionCallback {

                override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                    if (all) {
                        onRequestCallback.invoke()
                    } else {
                        DToastUtils.show("获取部分权限成功，但部分权限未正常授予")
                    }
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    if (never) {
                        DToastUtils.show("被永久拒绝授权，请手动授予权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(
                            ActivityManager.getInstance().getTopActivity(), permissions
                        )
                    } else {
                        DToastUtils.show("获取权限失败")
                    }
                }
            })
    }
}