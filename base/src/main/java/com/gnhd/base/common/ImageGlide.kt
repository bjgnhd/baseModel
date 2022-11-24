package com.gnhd.base.common


import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.gnhd.base.R
import com.gnhd.base.base.application
import me.bzcoder.easyglide.EasyGlide.loadImage
import me.bzcoder.easyglide.config.GlideConfigImpl
import me.bzcoder.easyglide.progress.*
import me.bzcoder.easyglide.transformation.*

object ImageGlide {
    var placeHolderImageView = R.color.transparent
    var circlePlaceholderImageView = R.color.transparent

    /**
     * 加载本地图片
     *
     * @param context
     * @param drawableId
     */

    @JvmStatic
    fun ImageView.load(@RawRes @DrawableRes drawableId: Int) {
        loadImage(
            application.applicationContext, GlideConfigImpl
                .builder()
                .drawableId(drawableId)

                .imageView(this)
                .build()
        )

    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.load(
        url: String?,
        @DrawableRes placeHolder: Int = placeHolderImageView,
        onProgressListener: OnProgressListener? = null,
        requestListener: RequestListener<Drawable?>? = null
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)

                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .progressListener(onProgressListener)
                .requestListener(requestListener)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadCircle(
        url: String?,
        @DrawableRes placeHolder: Int = circlePlaceholderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .isCropCircle(true)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }


    @JvmStatic
    @JvmOverloads
    fun ImageView.loadBlur(
        url: String?,
        radius: Int = 10,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation( BlurTransformation(context, radius))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadRoundCorner(
        url: String?,
        radius: Int = 40,
        margin: Int = 0,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(RoundedCornersTransformation(radius, margin))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadCircleWithBorder(
        url: String?,
        borderWidth: Int = 2,
        @ColorInt borderColor: Int = 0xACACAC,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CircleWithBorderTransformation(borderWidth, borderColor))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadBorder(
        url: String?,
        borderWidth: Int = 2,
        @ColorInt borderColor: Int = 0xACACAC,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(BorderTransformation(borderWidth, borderColor))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }
}

