import 'package:flutter/material.dart';

//    this.fit            缩放的方式。默认的属性是BoxFit.contain。
//                        child在FittedBox范围内，尽可能的大，但是不超出其尺寸。这里注意一点，contain是保持着child宽高比的大前提下，尽可能的填满，一般情况下，宽度或者高度达到最大值时，就会停止缩放
//    this.alignment      对齐方式。默认的属性是Alignment.center，居中显示child

void main() {
  runApp(new MyFittedBoxAppDemo());
}

class MyFittedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyFittedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MyFittedBoxAppDemo"),
        ),
        body: new FittedBoxDemo(),
      ),
    );
  }
}

class FittedBoxDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FittedBoxDemoState();
  }
}

class _FittedBoxDemoState extends State<FittedBoxDemo> {
  @override
  Widget build(BuildContext context) {
    return new Container(
      height: 300,
      width: 300,
      color: Colors.green,
      child: new FittedBox(
//        fit: BoxFit.cover,
//      fit: BoxFit.fitHeight,
//        fit: BoxFit.fitWidth,
//      fit: BoxFit.none,
        alignment: Alignment.topLeft,
        child: new Container(
          color: Colors.blue,
          child: new Text("FittedBoxDemo"),
        ),
      ),
    );
  }
}
