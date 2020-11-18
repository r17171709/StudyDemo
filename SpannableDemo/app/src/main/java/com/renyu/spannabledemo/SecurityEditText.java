package com.renyu.spannabledemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import androidx.appcompat.widget.AppCompatEditText;

public class SecurityEditText extends AppCompatEditText {

    private OnDelKeyEventListener delKeyEventListener;

    public SecurityEditText(Context context) {
        super(context);
    }

    public SecurityEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecurityEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new ZanyInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class ZanyInputConnection extends InputConnectionWrapper {

        public ZanyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if (delKeyEventListener != null) {
                    delKeyEventListener.onDeleteClick();
                }
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param delKeyEventListener EditText删除回调
     */
    public void setDelKeyEventListener(OnDelKeyEventListener delKeyEventListener) {
        this.delKeyEventListener = delKeyEventListener;
    }

    public interface OnDelKeyEventListener {
        void onDeleteClick();
    }

}
