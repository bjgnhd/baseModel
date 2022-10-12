package com.gnhd.base.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView


/**
 * <pre>
 *     @author : Trial
 *     @time   : 11/22/21
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
abstract class BaseLazyFragment<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {

    lateinit var binding: B

    /**
     * Fragment的View加载完毕的标记
     */
    private var isViewCreated = false

    /**
     * Fragment对用户可见的标记
     */
    private var isDataLoaded = false
    private var loadingPopup: LoadingPopupView? = null
    protected abstract fun initView()
    protected abstract fun initData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
    }
    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareRequestData()
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        isViewCreated = true
        prepareRequestData()
        super.onActivityCreated(savedInstanceState)
    }

    private fun prepareRequestData(forceUpdate: Boolean = false): Boolean {
        if (userVisibleHint && isViewCreated && (!isDataLoaded || forceUpdate)) {
            try {
                initView()
                initData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isDataLoaded = true
            return true
        }
        return false
    }
    fun showLoadingDialog() {
        if (loadingPopup == null) {
            loadingPopup = XPopup.Builder(requireActivity())
//                .dismissOnBackPressed(false)
                .asLoading("")
                .show() as LoadingPopupView
        } else {
            loadingPopup?.show()
        }
    }

    fun dismissLoadingDialog() {
        loadingPopup?.smartDismiss()
    }
}