import 'package:flutter/material.dart';

void main() {
  runApp(new MyFlowAppDemo());
}

class MyFlowAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      title: "MyFlowAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("FlowApp"),
        ),
        body: new Flow(
          delegate: new TestFlowDelegate(margin: EdgeInsets.all(5.0)),
          children: <Widget>[
            new Container(
              color: Colors.red,
              height: 50,
              width: 100,
            ),
            new Container(
              color: Colors.orange,
              height: 50,
              width: 50,
            ),
            new Container(
              color: Colors.yellow,
              height: 50,
              width: 150,
            ),
            new Container(
              color: Colors.green,
              height: 50,
              width: 100,
            ),
            new Container(
              color: Colors.lightBlue,
              height: 150,
              width: 50,
            ),
            new Container(
              color: Colors.blue,
              height: 50,
              width: 100,
            ),
            new Container(
              color: Colors.purple,
              height: 50,
              width: 200,
            )
          ],
        ),
      ),
    );
  }
}

class TestFlowDelegate extends FlowDelegate {
  EdgeInsets margin = EdgeInsets.zero;

  TestFlowDelegate({this.margin});

  @override
  void paintChildren(FlowPaintingContext context) {
    double x = margin.left;
    double y = margin.top;

    for (int i = 0; i < context.childCount; i++) {
      double width = context.getChildSize(i).width + x + margin.right;
      if (width <= context.size.width) {
        context.paintChild(i, transform: Matrix4.translationValues(x, y, 0.0));
        x += width + margin.left;
      } else {
        x = margin.left;
        y += context.getChildSize(i - 1).height + margin.bottom + margin.top;
        context.paintChild(i, transform: Matrix4.translationValues(x, y, 0.0));
        x = margin.left + context.getChildSize(i).width + margin.right;
      }
    }
  }

  @override
  Size getSize(BoxConstraints constraints) {
    return new Size(double.infinity, 400.0);
  }

  @override
  bool shouldRepaint(FlowDelegate oldDelegate) {
    // TODO: implement shouldRepaint
    return oldDelegate != this;
  }
}
