import 'package:flutter/material.dart';

void main() {
  runApp(new MyColumnAppDemo());
}

class MyColumnAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyColumnAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ColumnDemo"),
        ),
        body: new ColumnDemo(),
      ),
    );
  }
}

//class ColumnDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Column(
//      children: <Widget>[
//        new Text('ColumnDemo1'),
//        new Text('ColumnDemo2'),
//        const FlutterLogo(),
//      ],
//    );
//  }
//}

class ColumnDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Text('ColumnDemo1'),
        new Text('ColumnDemo2'),
        // 通Row一样，使用Expanded之后可以撑满主轴方向
        new Expanded(
            // 为了防止FlutterLogo过小，使用了FittedBox用以完整显示图片
            child: new FittedBox(
          fit: BoxFit.cover,
          child: const FlutterLogo(),
        )),
      ],
    );
  }
}
