package com.gnhd.base.base

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gnhd.base.R
import com.gnhd.base.aop.SingleClick
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.zackratos.ultimatebarx.ultimatebarx.getStatusBarOnly
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly


/**
 * <pre>
 *     @author : Trial
 *     @time   : 11/22/21
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
    AppCompatActivity(contentLayoutId) {
    lateinit var binding: B
    lateinit var rootView: View
    private var loadingPopup: LoadingPopupView? = null


    override fun setContentView(layoutResId: Int) {
        rootView = layoutInflater.inflate(layoutResId, null)
        setContentView(rootView)
        if (needSetStatusBar()) {
            initStatusBar()
        }
        binding = DataBindingUtil.bind(rootView)!!
        init()
    }

    private fun init() {
        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SingleClick
    protected abstract fun initView()

    @SingleClick
    protected abstract fun initData()

    open fun needSetStatusBar(): Boolean {
        return true
    }

    private fun initStatusBar() {
        // 只需要设置状态栏，不需要设置导航栏
        statusBarOnly {
            // 布局是否侵入状态栏（true 不侵入，false 侵入）
            fitWindow = true
            // 状态栏背景颜色（资源 id）
            color = Color.parseColor(MAIN_COLOR)
            // 状态栏背景 drawable
//            drawableRes = R.drawable.bg_common
            // 以上三个设置背景的方法用一个即可，如多次设置，后面的会把前面的覆盖掉
            // light模式：状态栏字体 true: 灰色，false: 白色 Android 6.0+
            light = false
            // 低版本 light 模式不生效，重新设置状态栏背景
            // 防止状态栏背景色跟字体颜色一致导致字体看不见
            // lvl 系列方法仅在低版本（不支持 light 模式的版本）下开启 light 模式生效
            lvlColor = Color.parseColor(MAIN_COLOR)
//            lvlColorRes = getResColor(R.color.main_color)
//            lvlDrawableRes = R.drawable.bg_lvl
            // 以上三个 lvl 方法用一个即可，如多次设置，后面的会把前面的覆盖掉
        }
    }

    protected fun setStatusBar(
        _fitWindow: Boolean = true,
        _color: Int = Color.parseColor(MAIN_COLOR),
        _light: Boolean = false
    ) {
        getStatusBarOnly {
            fitWindow = _fitWindow
            color = _color
            light = _light
            lvlColor = _color
        }
    }

    fun showLoadingDialog() {
        if (loadingPopup == null) {
            loadingPopup = XPopup.Builder(this)
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