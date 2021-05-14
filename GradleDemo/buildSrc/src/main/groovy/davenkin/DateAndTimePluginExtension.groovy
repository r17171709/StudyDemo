package davenkin

// 在DateAndTimePlugin中，我们向Project中定义了一个名为dateAndTime的extension，并向其中加入了2个Property，分别为timeFormat和dateFormat
class DateAndTimePluginExtension {
    String timeFormat = "MM/dd/yyyyHH:mm:ss.SSS"
    String dateFormat = "yyyy-MM-dd"
}