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
    fun ImageView.loadImage(@RawRes @DrawableRes drawableId: Int) {
        loadImage(
            application.applicationContext, GlideConfigImpl
                .builder()
                .drawableId(drawableId)
                .isCropCenter(true)
                .imageView(this)
                .build()
        )

    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadImage(
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
                .isCropCenter(true)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .progressListener(onProgressListener)
                .requestListener(requestListener)
                .build()
        )
    }

    /**
     * 加载本地图片
     * @param context
     * @param resizeX
     * @param resizeY
     */
    @JvmStatic
    @JvmOverloads
    fun ImageView.loadResizeXYImage(
        url: String?,
        resizeX: Int,
        resizeY: Int,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .isCropCenter(true)
                .isCrossFade(true)
                .resize(resizeX, resizeY)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadCircleImage(
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
    fun ImageView.loadGrayImage(
        url: String?,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CenterCrop(), GrayscaleTransformation())
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadBlurImage(
        url: String?,
        radius: Int = 10,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CenterCrop(), BlurTransformation(context, radius))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadRoundCornerImage(
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
                .transformation(CenterCrop(), RoundedCornersTransformation(radius, margin))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmStatic
    @JvmOverloads
    fun ImageView.loadCircleWithBorderImage(
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
    fun ImageView.loadBorderImage(
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

    /**
     * 提供了一下如下变形类，支持叠加使用
     * BlurTransformation
     * GrayScaleTransformation
     * RoundedCornersTransformation
     * CircleCrop
     * CenterCrop
     */
    @JvmStatic
    fun ImageView.loadImageWithTransformation(
        url: String?,
        vararg bitmapTransformations: BitmapTransformation,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            application.applicationContext,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(*bitmapTransformations)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

}

