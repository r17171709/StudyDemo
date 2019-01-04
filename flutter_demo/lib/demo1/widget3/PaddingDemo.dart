import 'package:flutter/material.dart';

void main() {
  runApp(new MyPaddingAppDemo());
}

class MyPaddingAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyPaddingAppDemo",
      theme: new ThemeData(
        primarySwatch: Colors.red
      ),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("PaddingDemo"),
        ),
        body: new PaddingDemo(),
      ),
    );
  }
}

class PaddingDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _PaddingDemoState();
  }
}

// 当Padding没有子节点（child）的时候，会产生一个宽为left+right，高为top+bottom的区域
//class _PaddingDemoState extends State<PaddingDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.green,
//      child: new Padding(padding: EdgeInsets.all(10.0)),
//    );
//  }
//}

// 当child不为空的时候，Padding会将布局约束传递给child，根据设置的padding属性，缩小child的布局尺寸
class _PaddingDemoState extends State<PaddingDemo> {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.green,
      child: new Padding(padding: EdgeInsets.all(10.0), child: new Container(
        height: 100.0,
        width: 100.0,
        color: Colors.red,
      ),),
    );
  }
}