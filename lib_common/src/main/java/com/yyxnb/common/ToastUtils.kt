package com.yyxnb.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.yyxnb.common.AppConfig.getContext
import java.io.Serializable

/**
 * 自定义Toast
 *
 * @author yyx
 */
object ToastUtils : Serializable {
    @ColorInt
    private val DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")

    @ColorInt
    private val ERROR_COLOR = Color.parseColor("#D8524E")

    @ColorInt
    private val INFO_COLOR = Color.parseColor("#3278B5")

    @ColorInt
    private val SUCCESS_COLOR = Color.parseColor("#5BB75B")

    @ColorInt
    private val WARNING_COLOR = Color.parseColor("#FB9B4D")

    @ColorInt
    private val NORMAL_COLOR = Color.parseColor("#444344")
    private const val TOAST_TYPEFACE = "sans-serif-condensed"
    private val context = getContext()

    /**
     * 上次显示的内容
     */
    private val oldMsg: String? = null

    /**
     * 上次时间
     */
    private const val oldTime: Long = 0

    /**
     * Toast对象
     */
    private var mToast: Toast? = null
    fun normal(message: String, icon: Drawable?): Toast? {
        return normal(message, Toast.LENGTH_SHORT, icon)
    }

    @JvmOverloads
    fun normal(message: String, duration: Int = Toast.LENGTH_SHORT,
               icon: Drawable? = null): Toast? {
        return custom(message, icon, NORMAL_COLOR, duration)
    }

    fun info(message: String): Toast? {
        return custom(message, null, INFO_COLOR)
    }

    fun warning(message: String): Toast? {
        return custom(message, null, WARNING_COLOR)
    }

    fun success(message: String): Toast? {
        return custom(message, null, SUCCESS_COLOR)
    }

    fun error(message: String): Toast? {
        return custom(message, null, ERROR_COLOR)
    }

    fun custom(message: String, @ColorInt tintColor: Int): Toast? {
        return custom(message, null, DEFAULT_TEXT_COLOR, tintColor, Toast.LENGTH_SHORT)
    }

    fun custom(message: String, icon: Drawable?, @ColorInt tintColor: Int): Toast? {
        return custom(message, icon, DEFAULT_TEXT_COLOR, tintColor, Toast.LENGTH_SHORT)
    }

    fun custom(message: String, @ColorInt tintColor: Int, duration: Int): Toast? {
        return custom(message, null, DEFAULT_TEXT_COLOR, tintColor, duration)
    }

    fun custom(message: String, icon: Drawable?, @ColorInt tintColor: Int, duration: Int): Toast? {
        return custom(message, icon, DEFAULT_TEXT_COLOR, tintColor, duration)
    }

    fun custom(message: String, @DrawableRes iconRes: Int,
               @ColorInt textColor: Int, @ColorInt tintColor: Int, duration: Int): Toast? {
        return custom(message, getDrawable(context!!, iconRes), textColor, tintColor, duration)
    }

    /**
     * 自定义toast方法
     *
     * @param message   提示消息文本
     * @param icon      提示消息的icon,传入null代表不显示
     * @param textColor 提示消息文本颜色
     * @param tintColor 提示背景颜色
     * @param duration  显示时长
     * @return
     */
    fun custom(message: String, icon: Drawable?, @ColorInt textColor: Int, tintColor: Int, duration: Int): Toast? {
        if (mToast == null) {
            mToast = Toast(context)
        } else {
            mToast!!.cancel()
            mToast = null
            mToast = Toast(context)
        }
        @SuppressLint("InflateParams") val toastLayout = (context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.toast_layout, null)
        val toastIcon = toastLayout.findViewById<ImageView>(R.id.toast_icon)
        val toastText = toastLayout.findViewById<TextView>(R.id.toast_text)
        if (tintColor != -1) {
            setBackground(toastLayout, getDrawable(context, tintColor))
        }
        if (icon == null) {
            toastIcon.visibility = View.GONE
        } else {
            setBackground(toastIcon, icon)
        }
        toastText.setTextColor(textColor)
        toastText.text = message
        toastText.typeface = Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL)
        mToast!!.view = toastLayout
        mToast!!.duration = duration
        return mToast
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setBackground(view: View, drawable: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = drawable
        } else {
            view.setBackgroundDrawable(drawable)
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(id)
        } else {
            context.resources.getDrawable(id)
        }
    }
}