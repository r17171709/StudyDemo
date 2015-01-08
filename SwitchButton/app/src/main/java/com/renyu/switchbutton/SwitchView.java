package com.renyu.switchbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ry on 2014/12/20.
 */
public class SwitchView extends View {

    Bitmap switchOnBkg=null;
    Bitmap switchOffBkg=null;
    Bitmap slipSwitchButton=null;

    boolean isSlipping=false;
    boolean isSwitchOn=false;
    int currentX=0;
    //初始点击位置
    int startX=0;

    OnSwitchChangedLister lister=null;

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void setOnSwitchChangedLister(OnSwitchChangedLister lister) {
        this.lister=lister;
    }

    private void init(Context context) {
        switchOnBkg = BitmapFactory.decodeResource(context.getResources(), R.drawable.switch_bkg_switch);
        switchOffBkg = BitmapFactory.decodeResource(context.getResources(), R.drawable.switch_bkg_switch);
        slipSwitchButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.switch_btn_slip);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchOnBkg.getWidth(), switchOnBkg.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float leftSlipBtnX=0;

        canvas.drawBitmap(switchOnBkg, new Matrix(), new Paint());

        if (isSlipping) {
            if (currentX>switchOnBkg.getWidth()-slipSwitchButton.getWidth()) {
                leftSlipBtnX=switchOnBkg.getWidth()-slipSwitchButton.getWidth();
            }
            else if (currentX<0) {
                leftSlipBtnX=0;
            }
            else {
                leftSlipBtnX=currentX;
            }
        }
        else {
            if (isSwitchOn) {
                leftSlipBtnX=switchOnBkg.getWidth()-slipSwitchButton.getWidth();
            }
            else {
                leftSlipBtnX=0;
            }
        }
        canvas.drawBitmap(slipSwitchButton, leftSlipBtnX, 0, new Paint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isSlipping=true;
                currentX = (int) event.getX();
                System.out.println(currentX);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isSlipping=false;
                if (startX<currentX) {
                    isSwitchOn=true;
                }
                else {
                    isSwitchOn=false;
                }
                if (lister!=null) {
                    lister.onChnage(isSwitchOn);
                }
                invalidate();
                break;
        }
        return true;
    }

    public interface OnSwitchChangedLister {
        public void onChnage(boolean isOpen);
    }
}
