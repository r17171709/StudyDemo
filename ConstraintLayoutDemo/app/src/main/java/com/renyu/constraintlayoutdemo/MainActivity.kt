package com.renyu.constraintlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 参考 https://github.com/feng0403/ConstraintLayoutSamples
 *     https://mp.weixin.qq.com/s/pF1Xo-CzNkWN706Be7XPnw
 *     https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置了其中一个margin，并不会影响关联的其他控件的位置
//        setContentView(R.layout.activity_main)
        // 底部baseline对齐
//        setContentView(R.layout.activity_baseline)
        // 角度、距离对齐
//        setContentView(R.layout.activity_circleangle)
        // goneMargin的使用
//        setContentView(R.layout.activity_margin)
        // 偏移
//        setContentView(R.layout.activity_bias)
        // 当宽或高至少有一个尺寸被设置为0dp时,那么系统就认为我们要以另外一方为准去约束
        // width/height。要使其生效的话，必须将对应的宽或高设置为0dp。例如宽为0dp，高为10dp，app:layout_constraintDimensionRatio=2:1之后，宽最终为20dp
//        setContentView(R.layout.activity_constrained)
        // 一条链的第一个控件是这条链的链头，我们可以在链头中设置layout_constraintHorizontal_chainStyle来改变整条链的样式
        // 可以通过app:layout_constraintHorizontal_weight/app:layout_constraintVertical_weight分别设置水平和垂直链上的权重
//        setContentView(R.layout.activity_chain)
        // 在多个控件的一侧建立一个屏障
//        setContentView(R.layout.activity_barrier)
        // Group可以把多个控件归为一组，方便隐藏或显示一组控件
//        setContentView(R.layout.activity_group)
        // 在Placeholder中可使用content设置另一个控件的id，使那个控件移动到占位符的位置
//        setContentView(R.layout.activity_placeholder)
        // 辅助线帮助布局
//        setContentView(R.layout.activity_guide)
        // ConstraintHelper辅助工具：Layer。功能上可以理解为包含它所引用的view的一个父布局viewGroup，但并不会增加layout的层级。支持对里面的view一起做变换
//        setContentView(R.layout.activity_test)
        // 通过flow_wrapMode可以指定具体的排列方式,有三种模式
        // none : 简单地把constraint_referenced_ids里面的元素组成chain,即使空间不够
        // chain : 根据空间的大小和元素的大小组成一条或者多条chain
        // aligned : wrap chain类似，但是会对齐
//        setContentView(R.layout.activity_flow)
        // ImageFilterButton、ImageFilterView 实现圆角图片，圆形图片以及色温、亮度、饱和度
//        setContentView(R.layout.activity_imagefilter)
        // MotionLayout设置场景切换
        // 每个MotionLayout需引用一个 MotionScene，其中包含相应布局的所有运动描述
//        setContentView(R.layout.activity_motion1)
        // 描述运动起终点的各种约束条件
//        setContentView(R.layout.activity_motion2)
        // KeyPosition可以改变动画运动过程中的位置，比如之前我们实现的运动都是直线运行，现在就可以用KeyPosition来实现曲线运动。重点说一下keyPositionType：1.parentRelative表示以MotionLayout 布局为参考系 2. deltaRelative表示视图的起始点坐标为(0,0), 终点坐标为(1,1)  3. pathRelative表示起始点(0,0)是还是视图开始位置，视图的终点位置是(1,0)
        // keyAttribute可以让我们改变在动画的过程中某个时刻的属性。KeyAttibute定义的是的Android中View原生属性，支持的属性如下： android:visibility, android:alpha, android:elevation, android:rotation, android:rotationX, android:rotationY, android:scaleX, android:scaleY, android:translationX, android:translationY, android:translationZ。如果是自定义的属性，可以使用KeyAttibute的子元素CustomAttribute
        // KeyTimeCycle表示循环动画，他会跟一直显示在View上，不会随着动画的变化而消失
        // KeyTrigger  在动画的过程中可以触发视图中的函数。onCross值为要触发的函数名
        // 参考 https://blog.csdn.net/knight1996/article/details/108015536
//        setContentView(R.layout.activity_motion3)
//        setContentView(R.layout.activity_motion4)
//        setContentView(R.layout.activity_motioneffect)
//        setContentView(R.layout.activity_circleangle)
        setContentView(R.layout.activity_motion_viewtransition)
    }
}