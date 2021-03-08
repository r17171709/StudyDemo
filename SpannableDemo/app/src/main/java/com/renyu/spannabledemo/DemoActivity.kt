package com.renyu.spannabledemo

import android.graphics.*
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.renyu.spannabledemo.databinding.ActivityDemoBinding
import java.util.*

/**
 * 参考文章： https://xuyisheng.top/span1/
 */
class DemoActivity : AppCompatActivity() {
    private var binding: ActivityDemoBinding? = null

    private var underlineSpan: UnderlineSpan? = null
    private var foregroundColorSpan: ForegroundColorSpan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(root)

            val string =
                SpannableString("Text with underline span Text with underline span Text with underline span")

            // 对文本变化的监听回调
            edDemo.setEditableFactory(DemoSpanFactory(DemoSpanWatcher()))

            // 下划线
            underlineSpan = UnderlineSpan()
            string.setSpan(underlineSpan, 1, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            string.setSpan(UnderlineSpan(), 6, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            string.setSpan(UnderlineSpan(), 11, 14, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 中划线
//            string.setSpan(StrikethroughSpan(), 1, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 修改尺寸
//            string.setSpan(RelativeSizeSpan(2f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 背景色
//            string.setSpan(BackgroundColorSpan(Color.RED), 1, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 前景色
            foregroundColorSpan = ForegroundColorSpan(Color.RED)
            string.setSpan(foregroundColorSpan, 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 引用样式，在文本左侧添加一条表示引用的竖线
//            string.setSpan(QuoteSpan(Color.RED), 0, string.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 段落前圆点
//            string.setSpan(BulletSpan(15, Color.RED), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            // 点击事件
//            string.setSpan(object : ClickableSpan() {
//                override fun onClick(widget: View) {
//                    Toast.makeText(this@DemoActivity, "Demo", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    ds.isUnderlineText = false
//                }
//            }, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 字体绝对大小（文本字体）
//            string.setSpan(AbsoluteSizeSpan(55, true), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 文本开头插入Drawable
//            string.setSpan(DrawableMarginSpan(resources.getDrawable(R.mipmap.ic_launcher, null), 10), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 模糊
//            string.setSpan(MaskFilterSpan(BlurMaskFilter(3f * 2, BlurMaskFilter.Blur.NORMAL)), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 浮雕
//            string.setSpan(MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f)), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 图片占位替换
//            string.setSpan(ImageSpan(this@DemoActivity, R.mipmap.ic_launcher), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 图片尺寸、位置
            val drawable = ContextCompat.getDrawable(this@DemoActivity, R.mipmap.ic_launcher)
            drawable?.setBounds(0, 0, 18, 18)
            string.setSpan(
                ImageSpan(drawable!!, DynamicDrawableSpan.ALIGN_BASELINE),
                0,
                2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // IconMarginSpan

            // ScaleXSpan

            // 字体样式：粗体、斜体等
//            string.setSpan(StyleSpan(Typeface.BOLD), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 上标
//            string.setSpan(SuperscriptSpan(), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 下标
//            string.setSpan(SubscriptSpan(), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 文本字体、大小、样式和颜色
//            string.setSpan(TextAppearanceSpan(this@DemoActivity, R.style.SpecialTextAppearance), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // TypefaceSpan

            // 文本超链接
//            val urlSpan = object : URLSpan("https://www.baidu.com") {
//                override fun updateDrawState(ds: TextPaint) {
//                    ds.isUnderlineText = false
//                }
//            }
//            string.setSpan(urlSpan, 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 文本对齐方式
//            string.setSpan(
//                AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
//                0,
//                string.length,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            )

            // 段落缩进
//            string.setSpan(LeadingMarginSpan.Standard(60, 20), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // LineBackgroundSpan是原生Span，它封装了对行对象的Background，其内部有个简单的实现—Standard
//            string.setSpan(LineBackgroundSpan.Standard(Color.CYAN), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 调整行高的Span
//            string.setSpan(LineHeightSpan.Standard(90), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 复合Span效果
//            string.setSpan(RelativeSizeColorSpan(3f), 1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            string.setSpan(FrameSpan(), 12, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            string.setSpan(TextRoundSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            string.setSpan(RainbowSpan(6), 17, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            tvDemo.text = string
            // ClickableSpan出现文字选中出现淡绿色的背景色现象
//            tvDemo.highlightColor = Color.TRANSPARENT
            tvDemo.movementMethod = LinkMovementMethod.getInstance()
            tvDemo.setOnClickListener {
                // 当点击ClickableSpan时，TextView的selectionStart和selectionEnd会改变，这时候就不用处理TextView的点击事件了，经过这层过滤，就实现了TextView和ClickableSpan的互斥点击
                if (tvDemo.selectionStart == -1 && tvDemo.selectionEnd == -1) {
                    Toast.makeText(this@DemoActivity, "Text", Toast.LENGTH_SHORT).show()
                }
            }

//            edDemo.setText(string)
            // Selection用于在Edittext中执行选中功能
            // 如果start != stop，那么start表示选择过程中不变的光标，stop表示变化的光标
            // 注意传递的参数值
//            Selection.setSelection(edDemo.text, 0, 19)

            // 获取一个span的开始位置
            SpannableString(tvDemo.text).getSpanStart(underlineSpan)
            // 获取一个span的结束位置
            SpannableString(tvDemo.text).getSpanEnd(underlineSpan)

            // 获取从start到end的位置上所有的特定类型的span
            // nextSpanTransition
            SpannableString(tvDemo.text).getSpans(0, 10, UnderlineSpan::class.java)

            // 移除指定的Span实例
//            SpannableString(tvDemo.text).removeSpan(foregroundColorSpan)
//            SpannableString(tvDemo.text).getSpanStart(underlineSpan)

            // 将当前Text下的所有Span实例给dump出来
//            TextUtils.dumpSpans(tvDemo.text, LogPrinter(Log.DEBUG, "PQ"), "spans: ")

            // Android7.0系统以上getSpans方法会返回乱序的排列数组，这跟它的内部实现有关
            val spans = orderSpans<UnderlineSpan>(tvDemo)

//            var startPosition = 0
//            while (startPosition != tvDemo.text.length) {
//                startPosition = SpannableString(tvDemo.text).nextSpanTransition(startPosition, tvDemo.text.length, UnderlineSpan::class.java)
//                if (startPosition != tvDemo.text.length) {
//                    // 分别迭代出起始点
//                }
//            }

            // setSpan的方法会传入一个xxxSpan，这个参数是个Any类型，你可以给它设置任何一个类
//            val user = User("renyu", "renyu")
//            SpannableString(tvDemo.text).setSpan(user, 50, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            val spans1 = orderSpans<User>(tvDemo)

        }
    }

    private inline fun <reified T> orderSpans(tvDemo: TextView): Array<T> {
        val spans: Array<T> =
            SpannableString(tvDemo.text).getSpans(0, tvDemo.text.length, T::class.java)
        Arrays.sort(spans) { o1, o2 ->
            return@sort SpannableString(tvDemo.text).getSpanStart(o1) - SpannableString(tvDemo.text).getSpanStart(
                o2
            )
        }
        return spans
    }
}

/**
 * 复合Span效果
 */
class RelativeSizeColorSpan(proportion: Float) : RelativeSizeSpan(proportion) {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = Color.BLUE
    }
}

/**
 * 边框标签
 */
class FrameSpan : ReplacementSpan() {
    private val paint = Paint()
    private var width = 0f
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        width = paint.measureText(text, start, end)
        return width.toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), paint)
        canvas.drawText(text.toString(), start, end, x, y.toFloat(), this@FrameSpan.paint)
    }

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.GREEN
        paint.isAntiAlias = true
    }
}

/**
 * Text留白
 */
class TextRoundSpan : LeadingMarginSpan.LeadingMarginSpan2 {
    override fun getLeadingMargin(first: Boolean): Int {
        return if (first) 100 else 0
    }

    override fun drawLeadingMargin(
        c: Canvas?,
        p: Paint?,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence?,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?
    ) {

    }

    override fun getLeadingMarginLineCount() = 1
}

class RainbowSpan(val textLength: Int) : CharacterStyle(), UpdateAppearance {
    override fun updateDrawState(tp: TextPaint?) {
        tp?.apply {
            style = Paint.Style.FILL
            val shader = LinearGradient(
                0f,
                0f,
                textSize * textLength,
                0f,
                Color.RED,
                Color.BLUE,
                Shader.TileMode.CLAMP
            )
            this.shader = shader
        }
    }
}