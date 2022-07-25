package com.gnhd.base.utils.permission

import androidx.fragment.app.FragmentActivity
import com.gnhd.base.manager.ActivityManager
import com.gnhd.base.utils.DToastUtils
import com.permissionx.guolindev.PermissionX

/**
 * <pre>
 * @author : Trial
 * @time   : 11/23/21
 * @desc   : 权限工具类
 * @version: 1.0
</pre> *
 */
object PermissionXUtil {
    /**
     * 如果授予此权限，则为 True，否则为 False
     */
    fun checkIsGranted(permission: String): Boolean {
        return PermissionX.isGranted(ActivityManager.getInstance().getTopActivity(), permission)
    }

    /**
     * 申请权限
     */
    fun checkPermission(onRequestCallback: () -> Unit = {}, vararg permission: String) {
        PermissionX.init(ActivityManager.getInstance().getTopActivity() as FragmentActivity)
            .permissions(*permission)
            .onExplainRequestReason { scope, deniedList, _ ->
                val mList= mutableListOf<String>()
                for (item in deniedList) {
                    mList.add(PermissionConstants.getPermissionName(item))
                }
                scope.showRequestReasonDialog(deniedList, "该功能需要以下权限才能使用", "确定", "取消")
            }
            .onForwardToSettings { scope, deniedList ->
                val mList= mutableListOf<String>()
                for (item in deniedList) {
                    mList.add(PermissionConstants.getPermissionName(item))
                }
                scope.showForwardToSettingsDialog(deniedList, "请在设置中允许以下权限", "去开启", "取消")
            }
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    onRequestCallback.invoke()
                } else {
                    val mList= mutableListOf<String>()
                    for (item in deniedList) {
                        mList.add(PermissionConstants.getPermissionName(item))
                    }
                    DToastUtils.show("您拒绝了如下权限：$mList")
                }
            }
    }
}