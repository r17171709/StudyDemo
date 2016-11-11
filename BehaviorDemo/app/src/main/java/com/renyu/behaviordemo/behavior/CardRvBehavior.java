package com.renyu.behaviordemo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.renyu.behaviordemo.view.RVView;

/**
 * Created by renyu on 2016/11/8.
 */

public class CardRvBehavior extends CoordinatorLayout.Behavior<RVView> {

    int defaultOffset=0;

    public CardRvBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, RVView child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int offset=getOffset(parent, child);
        //实际自定义View的高度
        int totalHeight= View.MeasureSpec.getSize(parentHeightMeasureSpec)-offset;
        //设置自定义View的尺寸
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(totalHeight, View.MeasureSpec.EXACTLY));
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RVView child, int layoutDirection) {
        //交由默认方式onLayoutChild去安排，此处我们仅仅是挪动位置而已
        parent.onLayoutChild(child, layoutDirection);
        RVView previous=getPreviousChild(parent, child);
        if (previous!=null) {
            int offset=previous.getTop()+previous.getHeadHeight();
            //移动自定义View位置
            child.offsetTopAndBottom(offset);
        }
        defaultOffset=child.getTop();
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RVView child, View directTargetChild, View target, int nestedScrollAxes) {
        boolean vertical=(nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
        //只有垂直方向且当前滚动的视图是自己，才响应
        return vertical && child==directTargetChild;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RVView child, View target, int dx, int dy, int[] consumed) {
        //target滑动之前的处理，这里RecyclerView的检测规则是：consumed[1]处理后返回给RecyclerView，只有这个值大于touchSlop，才能将后续事件传递给dispatchNestedScroll，也就是让RecyclerView自己滚动起来
        //因此在滑动范围内才需要处理，其余直接交给RecyclerView自行滚动
        if (child.getTop()>defaultOffset) {
            int min=defaultOffset;
            int max=child.getHeight()-child.getHeadHeight()+defaultOffset;
            consumed[1]=scroll(child, dy, min, max);
            scrollOther(consumed[1], coordinatorLayout, child);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RVView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //target滑动之后的处理，也就是RecyclerView自行滚动
        int min=defaultOffset;
        int max=child.getHeight()-child.getHeadHeight()+defaultOffset;
        int scrollY=scroll(child, dyUnconsumed, min, max);
        scrollOther(scrollY, coordinatorLayout, child);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, RVView child, View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }

    /**
     * 自身滚动处理，基本上设定在滑动区间范围
     * @param child
     * @param dy
     * @param min
     * @param max
     * @return
     */
    private int scroll(RVView child, int dy, int min, int max) {
        int top=child.getTop();
        if ((top-dy)>max) {
            child.offsetTopAndBottom(max-top);
            return -(max-top);
        }
        else if ((top-dy)<min) {
            child.offsetTopAndBottom(min-top);
            return -(min-top);
        }
        else {
            child.offsetTopAndBottom(-dy);
            return dy;
        }
    }

    private void scrollOther(int scrollY, CoordinatorLayout parent, RVView child) {
        //向下滚动
        if (scrollY<0) {
            RVView current=child;
            RVView next=getNextChild(parent, child);
            while (next!=null) {
                int delta=getOtherOffset(current, next);
                if (delta>0) {
                    next.offsetTopAndBottom(delta);
                }
                current=next;
                next=getNextChild(parent, next);
            }
        }
        //向上滚动
        else if (scrollY>0) {
            RVView current=child;
            RVView previous=getPreviousChild(parent, child);
            while (previous!=null) {
                int delta=getOtherOffset(previous,current);
                if (delta>0) {
                    previous.offsetTopAndBottom(-delta);
                }
                current=previous;
                previous=getPreviousChild(parent, previous);
            }
        }
    }

    /**
     * 计算得到自己上方的偏移量
     * @param parent
     * @param child
     * @return
     */
    private int getOffset(CoordinatorLayout parent, RVView child) {
        int offset=0;
        for (int i=0;i<parent.getChildCount();i++) {
            if (child!=parent.getChildAt(i) && parent.getChildAt(i) instanceof RVView) {
                offset+=((RVView) parent.getChildAt(i)).getHeadHeight();
            }
        }
        return offset;
    }

    /**
     * 不同自定义View之间的偏移量
     * @param child1
     * @param child2
     * @return
     */
    private int getOtherOffset(RVView child1, RVView child2) {
        //下方Head顶部与上方Head底部之间的距离
        return child1.getTop()+child1.getHeadHeight()-child2.getTop();
    }

    /**
     * 获取上一个自定义View
     * @param parent
     * @param child
     * @return
     */
    private RVView getPreviousChild(CoordinatorLayout parent, RVView child) {
        int position=parent.indexOfChild(child);
        for (int i=position-1;i>=0;i--) {
            if (parent.getChildAt(i) instanceof RVView) {
                return (RVView) parent.getChildAt(i);
            }
        }
        return null;
    }

    /**
     * 获取下一个自定义View
     * @param parent
     * @param child
     * @return
     */
    private RVView getNextChild(CoordinatorLayout parent, RVView child) {
        int position=parent.indexOfChild(child);
        for (int i=position+1;i<parent.getChildCount();i++) {
            if (parent.getChildAt(i) instanceof RVView) {
                return (RVView) parent.getChildAt(i);
            }
        }
        return null;
    }
}
