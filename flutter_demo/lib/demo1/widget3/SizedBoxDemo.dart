import 'package:flutter/material.dart';

//  child不为null时，如果SizedBox设置了宽高，则会强制把child尺寸调到此宽高；如果没有设置宽高，则会根据child尺寸进行调整；
//  child为null时，如果SizedBox设置了宽高，则自身尺寸调整到此宽高值；如果没设置，则尺寸为0；

void main() {
  runApp(new MySizedBoxAppDemo());
}

class MySizedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySizedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SizedBoxDemo"),
        ),
        body: new SizedBoxDemo(),
      ),
    );
  }
}

class SizedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new SizedBox(
      width: 200,
      height: 200,
      child: new Container(
        width: 300,
        height: 300,
        color: Colors.red,
      ),
    );
  }
}
