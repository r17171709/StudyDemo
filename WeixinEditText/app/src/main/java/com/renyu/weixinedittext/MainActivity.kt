package com.renyu.weixinedittext

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.renyu.weixinedittext.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: MainAdapter? = null

    var previousKeyboardHeight = -1

    private var dialog: Dialog? = null

    private val arrays: ArrayList<Any> by lazy {
        ArrayList<Any>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrays.add(TopClass(""))
        arrays.add("世界眼中的中国两会，是一道集聚全国人民磅礴之力的风景线，不仅展现着新时代中国的意气风发，而且增添着世界的生机和动能")
        arrays.add("中国智慧、中国方略、中国成就集中展现，来自世界的热评此起彼伏。“中国两会对全球事务有着巨大影响力”“是时候对中国的突飞猛进有清醒的认识了”“中国方案为世界提供借鉴”……来自世界的这些叙述，折射着探寻成功秘诀的目光。各国媒体纷纷向北京增派记者，3000多名中外记者报名采访中国两会。世界瞩目中国两会，期待进一步读懂新时代的中国。")
        arrays.add("作为世界最大发展中国家和第二大经济体，中国不断创造着人类发展史上惊天动地的奇迹。中国提供着最高的对世界经济增长的贡献率、最高的对世界减贫事业的贡献率，中国建成了世界上最大的社会保障网、高速铁路网，中国科技创新在诸多领域实现并跑、领跑……国际舆论普遍认为，着眼自身治理能力现代化的中国，日益为全球治理作出重要贡献。")
        arrays.add("世界经济复苏发展，中国功不可没。中国国家统计局最新数据显示，2017年，中国国内生产总值增速达6.9%，占世界经济的比重达15%，稳居世界第二。“中国的成功故事与世界经济的命运紧密相连。”国际货币基金组织第一副总裁戴维·利普顿这样评价。“世行对中国经济发展有信心，对中国持续成为全球经济增长引擎有信心。”世界银行发展预测局局长阿伊汗·高斯为中国点赞")
        arrays.add("国外不少人感叹，世界太需要像中国这样不断书写成功故事。但是，环顾当下的世界，一些国家深陷矛盾丛生、乱象频发的怪圈。在今年年初的达沃斯论坛年会和慕尼黑安全会议上，来自西方一些国家的人士纷纷大谈对社会分裂、政治极化、民粹主义盛行之忧。国际政治、社会领域诸多乱象，从若干侧面说明了失却方向之痛、道路探索之难。与此形成鲜明反差的是，中国的稳步发展和繁荣景象，无疑给不乏深忧的世界注入了昂扬振奋的力量。")
        arrays.add("中国的成功故事，揭示了中国道路自信、理论自信、制度自信、文化自信之源。中共十九大的胜利召开，为中国建设现代化强国指明了方向，为中华民族伟大复兴做了全面谋划。世界瞩目，十九大后的首次中国两会将如何围绕民众普遍关心的反腐倡廉、社会保障、教育公平、医疗改革、脱贫攻坚、依法治国、改革开放等一系列议题谋篇布局，中国智慧如何为破解各国面临的共同难题提供启示。")
        arrays.add("“立治有体，施治有序。”欧洲智库专家认为，中国在国家治理和推进改革方面展现的强大领导力，堪为世界各国推进改革的重要借鉴。不少报道过中国两会的外国记者表示，人大代表和政协委员积极为国家发展履职尽责、建言献策的情景让他们深刻体会到，中国的人民代表大会制度和中国共产党领导的多党合作和政治协商制度具有明显优越性，与西方一些执政党和反对党为了各自政治利益争论不休的场面有天壤之别。")
        arrays.add("站在新的历史起点上，中国蓄势待发。世界眼中的中国两会，是一道集聚全国人民磅礴之力的风景线，不仅展现着新时代中国的意气风发，而且增添着世界的生机和动能。")
        arrays.add("完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完完")

        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrays, this)
        rv_main.adapter = adapter

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            window.decorView.getWindowVisibleDisplayFrame(rect)
            val displayHeight = rect.bottom - rect.top
            val height = window.decorView.height
            val keyboardHeight = height - displayHeight
            if (previousKeyboardHeight != keyboardHeight) {
                val hide = displayHeight.toDouble() / height > 0.8
                if (hide) {
                    if (arrays[arrays.size - 1] is BottomClass) {
                        arrays.removeAt(arrays.size - 1)
                        adapter?.notifyDataSetChanged()
                    }
                    dialog?.dismiss()
                }
            }
        }
    }

fun showInputComment(commentView: View, position: Int) {
    // RV中评论区起始Y的位置
    val rvInputY = getY(commentView)
    val rvInputHeight = commentView.height

    dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
    dialog!!.setContentView(R.layout.dialog_comment)
    dialog!!.show()
    val handler = object : Handler() {}
    handler.postDelayed({
        // 对话框中的输入框Y的位置
        val dialogY = getY(dialog!!.findViewById<LinearLayout>(R.id.dialog_layout_comment))

        if (position == arrays.size - 1) {
            arrays.add(BottomClass(""))
            adapter?.height = dialog!!.findViewById<LinearLayout>(R.id.dialog_layout_comment).height
            adapter?.notifyDataSetChanged()
        }

        rv_main.smoothScrollBy(0, rvInputY - (dialogY - rvInputHeight))
    }, 300)
}

private fun getY(view: View): Int {
    val rect = IntArray(2)
    view.getLocationOnScreen(rect)
    return rect[1]
}
}

data class TopClass(val value: String)
data class BottomClass(val value: String)
