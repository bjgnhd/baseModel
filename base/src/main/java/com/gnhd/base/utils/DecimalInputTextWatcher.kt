package com.gnhd.base.utils

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.widget.EditText

/**
 * EditText 限制输入整数和小数 的位数
 * * 默认 整数位4位，小数位 最多2位
 */
class DecimalInputTextWatcher : TextWatcher {

    private var editText: EditText? = null
    private var decimalDigits // 小数的位数
            : Int
    private var integerDigits // 整数的位数
            = 0

    companion object {
        /**
         * 默认  整数的位数   4 位
         */
        private const val DEFAULT_INTEGER_DIGITS = 4

        /**
         * 默认  小数的位数   2 位
         */
        private const val DEFAULT_DECIMAL_DIGITS = 2
    }

    constructor(editText: EditText?) {
        this.editText = editText
        integerDigits = DEFAULT_INTEGER_DIGITS
        decimalDigits = DEFAULT_DECIMAL_DIGITS
    }

    /**
     * @param editText      editText
     * @param decimalDigits 小数的位数
     */
    constructor(editText: EditText?, decimalDigits: Int) {
        this.editText = editText
        if (decimalDigits <= 0) {
            throw RuntimeException("decimalDigits must > 0")
        }
        this.decimalDigits = decimalDigits
    }

    /**
     * @param editText      editText
     * @param integerDigits 整数的位数
     * @param decimalDigits 小数的位数
     */
    constructor(editText: EditText?, integerDigits: Int, decimalDigits: Int) {
        this.editText = editText
        if (integerDigits <= 0) {
            throw RuntimeException("integerDigits must > 0")
        }
        if (decimalDigits <= 0) {
            throw RuntimeException("decimalDigits must > 0")
        }
        this.integerDigits = integerDigits
        this.decimalDigits = decimalDigits
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {
        var s = editable.toString()
        editText!!.removeTextChangedListener(this)
        if (s.contains(".")) {
            if (integerDigits > 0) {
                editText!!.filters =
                    arrayOf<InputFilter>(LengthFilter(integerDigits + decimalDigits + 1))
            }
            if (s.length - 1 - s.indexOf(".") > decimalDigits) {
                s = s.substring(
                    0,
                    s.indexOf(".") + decimalDigits + 1
                )
                editable.replace(0, editable.length, s.trim { it <= ' ' })
            }
        } else {
            if (integerDigits > 0) {
                editText!!.filters = arrayOf<InputFilter>(LengthFilter(integerDigits + 1))
                if (s.length > integerDigits) {
                    s = s.substring(0, integerDigits)
                    editable.replace(0, editable.length, s.trim { it <= ' ' })
                }
            }
        }
        if (s.trim { it <= ' ' } == ".") {
            s = "0$s"
            editable.replace(0, editable.length, s.trim { it <= ' ' })
        }
        if (s.startsWith("0") && s.trim { it <= ' ' }.length > 1) {
            if (s.substring(1, 2) != ".") {
                editable.replace(0, editable.length, "0")
            }
        }
        if (mListener != null) {
            mListener!!.afterTextChanged(s)
        }
        editText!!.addTextChangedListener(this)
    }

    private var mListener: AfterTextChangedListener? = null
    fun setAfterTextChanged(listener: AfterTextChangedListener?) {
        mListener = listener
    }

    interface AfterTextChangedListener {
        fun afterTextChanged(str: String?)
    }


}