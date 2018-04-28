package com.example.tantan.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.animation.Interpolator;

public class ViewDragHelperUtils {

    public static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public static int computeSettleDuration(View mParentView, int dx, int dy, int xvel, int yvel, int mMinVelocity, int mMaxVelocity) {
        xvel = clampMag(xvel, mMinVelocity, mMaxVelocity);
        yvel = clampMag(yvel, mMinVelocity, mMaxVelocity);
        final int absDx = Math.abs(dx);
        final int absDy = Math.abs(dy);
        final int absXVel = Math.abs(xvel);
        final int absYVel = Math.abs(yvel);
        final int addedVel = absXVel + absYVel;
        final int addedDistance = absDx + absDy;

        final float xweight = xvel != 0 ? (float) absXVel / addedVel :
                (float) absDx / addedDistance;
        final float yweight = yvel != 0 ? (float) absYVel / addedVel :
                (float) absDy / addedDistance;

        int xduration = computeAxisDuration(mParentView, dx, xvel, 256);
        int yduration = computeAxisDuration(mParentView, dy, yvel, 256);

        return (int) (xduration * xweight + yduration * yweight);
    }

    private static int computeAxisDuration(View mParentView, int delta, int velocity, int motionRange) {
        if (delta == 0) {
            return 0;
        }

        final int width = mParentView.getWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, (float) Math.abs(delta) / width);
        final float distance = halfWidth + halfWidth
                * distanceInfluenceForSnapDuration(distanceRatio);

        int duration;
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float range = (float) Math.abs(delta) / motionRange;
            duration = (int) ((range + 1) * 256);
        }
        return Math.min(duration, 600);
    }

    private static int clampMag(int value, int absMin, int absMax) {
        final int absValue = Math.abs(value);
        if (absValue < absMin) return 0;
        if (absValue > absMax) return value > 0 ? absMax : -absMax;
        return value;
    }

    private static float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * (float) Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    /**
     * 判断点是否在View点击范围内
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchOnView(View view, float x, float y) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        return rect.contains((int) x, (int) y);
    }
}
