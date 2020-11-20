package com.renyu.coordinatorlaoyutdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.renyu.coordinatorlaoyutdemo.R;

// 重新摆放子View的位置
public class ViewAnchorBehavior extends CoordinatorLayout.Behavior<View> {
    // 关联对象ViewId
    int anchorId;

    public ViewAnchorBehavior() {
    }

    public ViewAnchorBehavior(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.view_anchor_behavior, 0, 0);
        anchorId = array.getResourceId(R.styleable.view_anchor_behavior_anchorId, 0);
        array.recycle();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return anchorId == dependency.getId();
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        View anchor = parent.findViewById(anchorId);
        if (anchor == null) {
            return false;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) anchor.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        int bottom = anchor.getBottom();
        // 再测量子View时，需要告诉CoordinatorLayout垂直方向上已经有多少空间被占用了
        heightUsed = topMargin + bottom;
        parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        View anchor = parent.findViewById(anchorId);
        if (anchor == null) {
            return false;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) anchor.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        int bottom = anchor.getBottom();
        parent.onLayoutChild(child, layoutDirection);
        // 偏移量
        child.offsetTopAndBottom(topMargin + bottom);
        return true;
    }
}
