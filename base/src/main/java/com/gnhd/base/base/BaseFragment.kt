package com.gnhd.base.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gnhd.base.aop.SingleClick
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
abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {

    lateinit var binding: B
    private var loadingPopup: LoadingPopupView? = null

    @SingleClick
    protected abstract fun initView()

    @SingleClick
    protected abstract fun initData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!

        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun needSetStatusBar(): Boolean {
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