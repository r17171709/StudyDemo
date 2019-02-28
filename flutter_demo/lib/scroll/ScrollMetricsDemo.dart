import 'package:flutter/material.dart';

void main() {
  runApp(new MyScrollMetricsAppDemo());
}

class MyScrollMetricsAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyScrollMetricsAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ScrollMetricsDemo"),
        ),
        body: new ScrollMetricsDemo(),
      ),
    );
  }
}

class ScrollMetricsDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ScrollMetricsDemoState();
  }
}

class _ScrollMetricsDemoState extends State<ScrollMetricsDemo> {
  @override
  Widget build(BuildContext context) {
    return new NotificationListener(
      child: new ListView.builder(
        itemBuilder: (BuildContext context, int index) {
          return new Row(
            children: <Widget>[
              new Container(
                width: MediaQuery.of(context).size.width,
                height: 50,
                alignment: AlignmentDirectional.centerStart,
                child: new Padding(
                  padding: EdgeInsets.only(left: 10),
                  child: new Text(
                    "$index",
                    style: new TextStyle(fontSize: 15),
                  ),
                ),
              )
            ],
          );
        },
        itemCount: 30,
      ),
      onNotification: (ScrollNotification notification) {
        print(
            "列表中未滑入ViewPort部分的长度: ${notification.metrics.extentAfter} "
                "滑出ViewPort顶部的长度: ${notification.metrics.extentBefore} "
                "ViewPort内部长度: ${notification.metrics.extentInside} "
                "最大可滚动长度: ${notification.metrics.maxScrollExtent} "
                "当前滚动位置: ${notification.metrics.pixels} "
                "是否滑到了边界: ${notification.metrics.atEdge}");
        return false;
      },
    );
  }
}
