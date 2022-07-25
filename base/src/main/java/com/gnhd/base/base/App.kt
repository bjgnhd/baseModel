package com.gnhd.base.base


import android.app.Application
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/18
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
lateinit var application: Application

inline val packageName: String get() = application.packageName

inline val packageInfo: PackageInfo
    get() = application.packageManager.getPackageInfo(packageName, 0)

inline val appName: String
    get() = application.applicationInfo.loadLabel(application.packageManager).toString()

inline val appVersionName: String get() = packageInfo.versionName

inline val appVersionCode: Long get() = PackageInfoCompat.getLongVersionCode(packageInfo)

