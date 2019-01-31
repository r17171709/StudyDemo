import 'package:flutter/material.dart';

// 根据parent的size创建一个view

void main() {
  runApp(new MyLayoutBuilderAppDemo());
}

class MyLayoutBuilderAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyLayoutBuilderAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("LayoutBuilderDemo"),
        ),
        body: new LayoutBuilderDemo(),
      ),
    );
  }
}

class LayoutBuilderDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _LayoutBuilderDemoState();
  }
}

class _LayoutBuilderDemoState extends State<LayoutBuilderDemo> {
  double width = 200;
  double height = 200;

  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.black,
      constraints: new BoxConstraints(maxWidth: width, maxHeight: height),
      child: new LayoutBuilder(
          // constraints 存储parent的信息
          builder: (BuildContext context, BoxConstraints constraints) {
        return new Container(
          color: Colors.blue,
          height: constraints.constrainHeight() * 2,
          width: constraints.constrainWidth() * 2,
        );
      }),
    );
  }
}
