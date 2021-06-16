package davenkin

import org.gradle.api.Action
import org.gradle.util.ConfigureUtil

// 在DateAndTimePlugin中，我们向Project中定义了一个名为dateAndTime的extension，并向其中加入了2个Property，分别为timeFormat和dateFormat
class DateAndTimePluginExtension {
    String timeFormat = "MM/dd/yyyy HH:mm:ss.SSS"
    String dateFormat = "yyyy-MM-dd"

    DateAndTimePluginInnerExtension innerExtension = new DateAndTimePluginInnerExtension()

    void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat
    }

    void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat
    }

    // 嵌套Extension的关键点在于下面这2个方法的定义，只需要定义任意一个即可：
    void inner(Action<DateAndTimePluginInnerExtension> action) {
        action.execute(innerExtension)
    }

//    void inner(Closure c) {
//        ConfigureUtil.configure(c, innerExtension)
//    }
}