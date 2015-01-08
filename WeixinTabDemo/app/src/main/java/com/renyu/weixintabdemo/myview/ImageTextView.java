package com.renyu.weixintabdemo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.weixintabdemo.R;

/**
 * Created by ry on 2014/12/15.
 */
public class ImageTextView extends RelativeLayout {

    int selectColor=0;
    int normalColor=0;

    public BarItemImageView getTabitem_image() {
        return tabitem_image;
    }

    BarItemImageView tabitem_image=null;
    TextView tabitem_text=null;

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        selectColor=context.getResources().getColor(R.color.select);
        normalColor=context.getResources().getColor(R.color.unselect);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tabitem_image=(BarItemImageView) findViewById(R.id.tabitem_image);
        tabitem_text=(TextView) findViewById(R.id.tabitem_text);
    }

    /**
     * 拼成颜色值
     * @param f
     * @return
     */
    private int getColorInt(float f) {
        int R1 = (selectColor & 0xff0000) >> 16;
        int G1 = (selectColor & 0xff00) >> 8;
        int B1 = (selectColor & 0xff);
        int R2 = (normalColor & 0xff0000) >> 16;
        int G2 = (normalColor & 0xff00) >> 8;
        int B2 = (normalColor & 0xff);
        int Rm = R1 - R2;
        int Gm = G1 - G2;
        int Bm = B1 - B2;
        int R = (int) (R2 + f * Rm);
        int G = (int) (G2 + f * Gm);
        int B = (int) (B2 + f * Bm);
        return 0xff << 24 | R << 16 | G << 8 | B;

    }

    public void setBarColor(float f) {
        tabitem_text.setTextColor(getColorInt(f));
    }
}
