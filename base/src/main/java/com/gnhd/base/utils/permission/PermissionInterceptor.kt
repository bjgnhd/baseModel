package com.gnhd.base.utils.permission

import android.app.Activity
import android.content.Context
import com.gnhd.base.R
import com.gnhd.base.utils.DToastUtils.show
import com.gnhd.base.utils.getResString
import com.hjq.permissions.IPermissionInterceptor
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.XXPermissions
import com.lxj.xpopup.XPopup

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/XXPermissions
 * time   : 2021/01/04
 * desc   : 权限申请拦截器
 */
class PermissionInterceptor : IPermissionInterceptor {
    override fun grantedPermissions(
        activity: Activity, allPermissions: List<String>, grantedPermissions: List<String>,
        all: Boolean, callback: OnPermissionCallback?
    ) {
        callback?.onGranted(grantedPermissions, all)
    }

    override fun deniedPermissions(
        activity: Activity, allPermissions: List<String>, deniedPermissions: List<String>,
        never: Boolean, callback: OnPermissionCallback?
    ) {
        callback?.onDenied(deniedPermissions, never)
        if (never) {
            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback)
            return
        }
        show(R.string.common_permission_fail_hint)
    }

    /**
     * 显示授权对话框
     */
    private fun showPermissionSettingDialog(
        activity: Activity?, allPermissions: List<String>,
        deniedPermissions: List<String>, callback: OnPermissionCallback?
    ) {
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return
        }
        XPopup.Builder(activity)
            .asConfirm(getResString(R.string.common_permission_alert),
                getPermissionHint(activity, deniedPermissions),
                "",
                getResString(R.string.common_permission_goto_setting_page),
                {
                    XXPermissions.startPermissionActivity(activity,
                        deniedPermissions, object : OnPermissionPageCallback {
                            override fun onGranted() {
                                if (callback == null) {
                                    return
                                }
                                callback.onGranted(allPermissions, true)
                            }

                            override fun onDenied() {
                                showPermissionSettingDialog(
                                    activity, allPermissions,
                                    XXPermissions.getDenied(activity, allPermissions), callback
                                )
                            }
                        })
                },
                null,
                true
            )
    }

    /**
     * 根据权限获取提示
     */
    private fun getPermissionHint(context: Context, permissions: List<String>?): String {
        if (permissions == null || permissions.isEmpty()) {
            return context.getString(R.string.common_permission_manual_fail_hint)
        }
        val hints = PermissionNameConvert.permissionsToStrings(context, permissions)
        return if (hints.isNotEmpty()) {
            context.getString(
                R.string.common_permission_manual_assign_fail_hint,
                PermissionNameConvert.listToString(hints)
            )
        } else context.getString(R.string.common_permission_manual_fail_hint)
    }
}