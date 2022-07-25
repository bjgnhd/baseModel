package com.gnhd.base.utils

import com.gnhd.base.listener.OnResultListener
import com.gnhd.base.manager.ActivityManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.File


/**
 * <pre>
 *     author : Administrator
 *     time   : 2022/05/31
 *     desc   : 选择图片工具类
 * </pre>
 */
object PictureSelectorUtils {
    /**
     * 单独拍照
     */
    fun openCamera(listener: OnResultListener?) {
        PictureSelector.create(ActivityManager.getInstance().getTopActivity())
            .openCamera(SelectMimeType.ofImage())
            .setCompressEngine(CompressFileEngine { context, source, call ->
                Luban.with(context).load(source).ignoreBy(80)
                    .setCompressListener(object : OnNewCompressListener {
                        override fun onStart() {

                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                        }
                    }).launch()
            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    listener?.onResult(result)
                }

                override fun onCancel() {
                    listener?.onCancel()
                }
            })
    }

    /**
     * 选择相册
     */
    fun selectPicture(
        isDisplayCamera: Boolean = true,
        maxSelectNum: Int = 1,
        listener: OnResultListener?
    ) {
        PictureSelector.create(ActivityManager.getInstance().getTopActivity())
            .openGallery(SelectMimeType.ofImage())
            .setMaxSelectNum(maxSelectNum)
            .isDisplayCamera(isDisplayCamera)
            .setImageEngine(CoilEngine())
            .setSandboxFileEngine { context, srcPath, mineType, call ->
                val copyPathToSandbox =
                    SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
                call?.onCallback(srcPath, copyPathToSandbox)
            }
            .setCompressEngine(CompressFileEngine { context, source, call ->
                Luban.with(context).load(source).ignoreBy(80)
                    .setCompressListener(object : OnNewCompressListener {
                        override fun onStart() {

                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                        }
                    }).launch()
            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    listener?.onResult(result)
                }

                override fun onCancel() {
                    listener?.onCancel()
                }
            })
    }
}