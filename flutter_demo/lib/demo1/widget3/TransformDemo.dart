import 'package:flutter/material.dart';
import 'dart:math';

void main() {
  runApp(new MyTransformAppDemo());
}

class MyTransformAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTransformAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("TransformDemo"),
        ),
        body: new TransformDemo(),
      ),
    );
  }
}

//class TransformDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Center(
//      child: new Container(
//        color: Colors.red,
//        height: 100.0,
//        width: 100.0,
//        alignment: AlignmentDirectional.center,
//        child: new Transform.scale(
//          scale: 2.0,
//          child: new Text("Transform.scale"),
//        ),
//      ),
//    );
//  }
//}

//class TransformDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Center(
//      child: new Container(
//        color: Colors.red,
//        height: 100.0,
//        width: 100.0,
//        alignment: AlignmentDirectional.center,
//        child: new Transform.translate(
//          offset: new Offset(100.0, 0.0),
//          child: new Text("Transform.translate"),
//        ),
//      ),
//    );
//  }
//}

//class TransformDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Center(
//      child: new Container(
//        color: Colors.red,
//        height: 100.0,
//        width: 100.0,
//        alignment: AlignmentDirectional.center,
//        child: new Transform.rotate(
//          angle: pi,
//          child: new Text("Transform.rotate"),
//        ),
//      ),
//    );
//  }
//}

class TransformDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Row(
      children: <Widget>[
        new RotatedBox(
          quarterTurns: 1,
          child: new Text("Transform.rotate"),
        ),
        new Text("Transform.rotate")
      ],
    );
  }
}
