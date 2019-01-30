import 'package:flutter/material.dart';

// 只显示指定位置的widget，其他的位置的widget不会显示，所以indexedStack的尺寸永远和最大的子节点一样

void main() {
  runApp(new MyIndexedStackAppDemo());
}

class MyIndexedStackAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyIndexedStackAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("IndexedStackDemo"),
        ),
        body: new IndexedStackDemo(),
      ),
    );
  }
}

class IndexedStackDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new IndexedStack(
      index: 1,
      children: <Widget>[
        new Container(
          color: Colors.red,
          width: 50,
          height: 50,
        ),
        new Container(
          color: Colors.blue,
          width: 50,
          height: 50,
        ),
        new Container(
          color: Colors.green,
          width: 50,
          height: 50,
        )
      ],
    );
  }
}
