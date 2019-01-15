import 'package:flutter/material.dart';

//  this.aspectRatio   aspectRatio是宽高比。最终可能不会根据这个值去布局，具体则要看综合因素，外层是否允许按照这种比率进行布局，只是一个参考值

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

class AspectRatioDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.green,
      width: 100.0,
      child: new AspectRatio(aspectRatio: 2.0),
    );
  }
}