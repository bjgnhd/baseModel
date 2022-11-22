package com.gnhd.base.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation


/**
 * <pre>
 *     @author : Trial
 *     @time   : 11/18/21
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
object CommonBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrlRadius", "placeHolder"], requireAll = false)
    fun loadUrlRadiusTen(view: ImageView, url: String?, placeHolder: Drawable?) {
        //1. 加载图片
        view.load(url) {
            transformations(RoundedCornersTransformation(10f))
            placeholder(placeHolder)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun loadUrl(view: ImageView, url: String?, placeHolder: Drawable?) {
        //1. 加载图片
        if (url.isNullOrEmpty()) {
            return
        }
        view.load(url) {
            placeholder(placeHolder)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["visible"], requireAll = false)
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["imageRes"], requireAll = false)
    fun setImageRes(imageView: ImageView, imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

}