package com.gnhd.base.widget

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import com.gnhd.base.R

import com.gnhd.base.utils.DLogUtil

/**
 * @Description:倒计时View
 */
class CountdownTextView constructor(context: Context, attrs: AttributeSet) :
    AppCompatTextView(context, attrs) {

    companion object {
        private const val TAG = "CountdownTextView"

        const val DEFAULT_COUNT_TIME = 60
        const val DEFAULT_INTERVAL = 1
        const val DEFAULT_LENGTH = 0
    }

    /**
     * 正常状态下的文字颜色
     */
    private var mNormalColor: Int = 0

    /**
     * 倒计时状态下的文字颜色
     */
    private var mCountdownColor: Int = 0

    /**
     * 总的时间
     */
    private var mCountTime: Int = 0

    /**
     * 时间间隔
     */
    private var mInterval: Int = 0

    /**
     * 绑定对应的EditText
     */
    private var mEditText: EditText? = null

    /**
     * 当与EditText绑定时，EditText输入达到触发控件使能的长度
     */
    private var mEnableLength: Int = 0

    /**
     * 是否正在倒计时
     */
    private var mTicking: Boolean = false

    private var mCountDownTimer: CountDownTimer? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CountdownTextView).apply {
            try {
                mNormalColor = getColor(R.styleable.CountdownTextView_normalColor, Color.RED)
                mCountdownColor = getColor(R.styleable.CountdownTextView_countdownColor, Color.GRAY)
                mCountTime = getInt(R.styleable.CountdownTextView_countTime, DEFAULT_COUNT_TIME)
                mInterval = getInt(R.styleable.CountdownTextView_interval, DEFAULT_INTERVAL)
                mEnableLength = getInt(R.styleable.CountdownTextView_enableLength, DEFAULT_LENGTH)
            } finally {
                recycle()
            }
        }

        text = resources.getText(R.string.send_code)
        isEnabled = true
    }

    /**
     * 绑定EditText
     */
    fun bindEt(et: EditText) {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!mTicking) {
                    isEnabled = et.text.length == mEnableLength
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        this.mEditText = et
        isEnabled = et.text.length == mEnableLength
    }

    /**
     * 开始倒计时
     */
    fun startTask() {
        cancelTask()

        //总时间加上100毫秒是为了补偿时间，不然会直接从低一秒开始跳（例如定的60秒，则会从59秒开始倒计时）
        mCountDownTimer = object : CountDownTimer(mCountTime * 1000L + 100, mInterval * 1000L) {
            override fun onFinish() {
                DLogUtil.d(TAG, "onFinish")
                post { toNormalState() }
            }

            override fun onTick(millisUntilFinished: Long) {
                DLogUtil.d(TAG, "onTick $millisUntilFinished")
                post {
                    text =
                        "${(millisUntilFinished / 1000)}s"
                }
            }
        }

        mCountDownTimer?.let {
            toCountdownState()
            it.start()
        }
    }

    /**
     * 取消倒计时
     */
    private fun cancelTask() {
        mCountDownTimer?.cancel()
        toNormalState()
    }

    /**
     * 切换到倒计时状态
     */
    private fun toCountdownState() {
        mTicking = true
        isEnabled = false
    }

    /**
     * 切换到‘获取验证码’字样的状态
     * 至于enable和颜色则取决于绑定的EditText上的字符串是否符合使能长度
     */
    private fun toNormalState() {
        mTicking = false
        isEnabled = mEditText?.let { it.text.length == mEnableLength } ?: true
        text = resources.getText(R.string.send_code)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            setTextColor(mNormalColor)
        } else {
            setTextColor(mCountdownColor)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelTask()
    }
}
