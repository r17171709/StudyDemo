import 'package:flutter/material.dart';

//  this.aspectRatio   aspectRatio是宽高比。最终可能不会根据这个值去布局，具体则要看外层是否有约束，没有约束的话则按照这种比例进行布局

void main() {
  runApp(new MyAspectRatioAppDemo());
}

class MyAspectRatioAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAspectRatioAppDemo",
      theme: new ThemeData(
        primarySwatch: Colors.red
      ),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AspectRatioDemo"),
        ),
        body: new AspectRatioDemo(),
      ),
    );
  }
}

//class AspectRatioDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      height: 50,
//      width: 50,
//      child: new Container(
//        color: Colors.green,
//        width: 100.0,
//        // 有外层约束，无法实现宽高2:1，只能以外层约束条件进行宽高设置
//        child: new AspectRatio(aspectRatio: 2.0),
//      ),
//    );
//  }
//}

class AspectRatioDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.green,
      width: 100.0,
      // 没有外层约束，可以实现宽高2:1
      child: new AspectRatio(aspectRatio: 2.0),
    );
  }
}