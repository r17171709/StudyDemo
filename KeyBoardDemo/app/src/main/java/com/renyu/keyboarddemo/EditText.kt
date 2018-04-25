package com.renyu.keyboarddemo

import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.KeyboardUtils

/**
 * Created by Administrator on 2018/3/8 0008.
 */
class KeyBoardEditText : AppCompatEditText {

    private var context_: Context? = null
    private var keyboradNumber: Keyboard? = null
    private var keyboradLetter: Keyboard? = null
    private var keyboardView: KeyboardView? = null
    private var viewGroup: ViewGroup? = null

    // 是否发生键盘切换
    var changeLetter = false

    // 是否为大写字母
    var isCapital = false

    var listener: OnKeyboardStateChangeListener? = null

    constructor(context: Context) : super(context) {
        context_ = context
        initEditView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context_ = context
        initEditView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        context_ = context
        initEditView()
    }

    private fun initEditView() {
        keyboradNumber = Keyboard(context_, R.xml.keyboard_number)
        keyboradLetter = Keyboard(context_, R.xml.keyboard_letter)
    }

    fun setKeyboardType(viewGroup: ViewGroup, keyboardView: KeyboardView, number: Boolean) {
        this.keyboardView = keyboardView
        this.viewGroup = viewGroup
        if (number) {
            keyboardView.keyboard = keyboradNumber
        }
        keyboardView.isPreviewEnabled = true
        keyboardView.setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
            override fun swipeRight() {

            }

            override fun swipeLeft() {

            }

            override fun swipeUp() {

            }

            override fun swipeDown() {

            }

            override fun onPress(primaryCode: Int) {
                canShowPreview(primaryCode)
            }

            override fun onRelease(primaryCode: Int) {

            }

            override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
                val editable = text
                val start = selectionStart
                // 删除功能
                if (primaryCode == Keyboard.KEYCODE_DELETE) {
                    if (editable.isNotEmpty() && start>0) {
                        editable.delete(start-1, start)
                    }
                }
                // 字母键盘与数字键盘切换
                else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
                    changeKeyBoard(!changeLetter)
                }
                // 完成
                else if (primaryCode == Keyboard.KEYCODE_DONE) {
                    keyboardView.visibility = View.GONE
                    viewGroup.visibility = View.GONE
                    listener?.hide()
                }
                // 切换大小写
                else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                    changeCapital(!isCapital)
                    keyboardView.keyboard = keyboradLetter
                }
                else {
                    editable.insert(start, Character.toString(primaryCode.toChar()))
                }
            }

            override fun onText(text: CharSequence?) {

            }
        })
    }

    fun changeKeyBoard(letter: Boolean) {
        changeLetter = letter
        if (changeLetter) {
            keyboardView?.keyboard = keyboradLetter
        }
        else {
            keyboardView?.keyboard = keyboradNumber
        }
    }

    fun canShowPreview(primaryCode: Int) {
        val nolists = arrayOf(Keyboard.KEYCODE_SHIFT, Keyboard.KEYCODE_MODE_CHANGE, Keyboard.KEYCODE_CANCEL,
                Keyboard.KEYCODE_DONE, Keyboard.KEYCODE_DELETE, Keyboard.KEYCODE_ALT, 32)
        keyboardView?.isPreviewEnabled = !nolists.contains(primaryCode)
    }

    fun changeCapital(isCapital: Boolean) {
        val lowercase = "abcdefghijklmnopqrstuvwxyz"

        val keyList = keyboradLetter?.keys
        keyList?.forEach {
            if (it?.label != null && lowercase.indexOf(it.label.toString().toLowerCase()) != -1) {
                if (isCapital) {
                    it.label = it.label.toString().toUpperCase()
                    it.codes[0] -= 32
                }
                else {
                    it.label = it.label.toString().toLowerCase()
                    it.codes[0] += 32
                }
            }
            if (it?.label!=null && it?.label == "小写" && isCapital) {
                it.label = "大写"
            }
            else if (it?.label!=null && it?.label == "大写" && !isCapital) {
                it.label = "小写"
            }
        }
        this.isCapital = isCapital
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        KeyboardUtils.hideSoftInput(this)
        if (event?.action == MotionEvent.ACTION_UP) {
            if (keyboardView?.visibility != View.VISIBLE) {
                keyboardView?.visibility = View.VISIBLE
                viewGroup?.visibility = View.VISIBLE
                listener?.show()
            }
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && keyboardView?.visibility != View.GONE
                && viewGroup?.visibility != View.GONE) {
            keyboardView?.visibility = View.GONE
            viewGroup?.visibility = View.GONE
            listener?.hide()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        KeyboardUtils.hideSoftInput(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        KeyboardUtils.hideSoftInput(this)
    }

    interface OnKeyboardStateChangeListener {
        fun show()
        fun hide()
    }
}