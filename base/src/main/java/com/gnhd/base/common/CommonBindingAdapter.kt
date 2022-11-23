package com.gnhd.base.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.gnhd.base.R
import com.gnhd.base.utils.getResDrawable
import me.bzcoder.easyglide.EasyGlide.loadImage
import me.bzcoder.easyglide.EasyGlide.loadRoundCornerImage


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
    fun loadRoundCornerImage(view: ImageView, url: String?, @DrawableRes placeHolder: Int?) {
        //1. 加载图片
        if (url.isNullOrEmpty()) {
            return
        }
        view.loadRoundCornerImage(
            view.context,
            url,
            radius = 10,
            placeHolder = placeHolder ?: R.drawable.ps_image_placeholder
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun loadUrl(view: ImageView, url: String?, @DrawableRes placeHolder: Int?) {
        //1. 加载图片
        if (url.isNullOrEmpty()) {
            return
        }
        view.loadImage(
            context = view.context,
            url = url,
            placeHolder = placeHolder ?: R.drawable.ps_image_placeholder
        )
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